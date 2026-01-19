package com.neeraj.auth.authservice.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neeraj.auth.authservice.audit.AuditEventType;
import com.neeraj.auth.authservice.dto.AuthResponse;
import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.dto.RegisterRequest;
import com.neeraj.auth.authservice.entity.RefreshToken;
import com.neeraj.auth.authservice.entity.Role;
import com.neeraj.auth.authservice.entity.User;
import com.neeraj.auth.authservice.exception.BadRequestException;
import com.neeraj.auth.authservice.exception.NotFoundException;
import com.neeraj.auth.authservice.exception.UnauthorizedException;
import com.neeraj.auth.authservice.repository.RoleRepository;
import com.neeraj.auth.authservice.repository.UserRepository;
import com.neeraj.auth.authservice.util.JwtService;
import com.neeraj.auth.authservice.util.RequestContext;
import com.neeraj.auth.authservice.util.SecurityConstants;
import com.neeraj.auth.authservice.service.RefreshTokenService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jjwtService;
    private final RefreshTokenService refreshTokenService;
    private final EmailVerificationService emailVerificationService;
    private final AuditLogService auditLogService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("ROLE_USER not found in DB"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 🔥 IMPORTANT
        user.setRoles(Set.of(userRole));
        user.setEnabled(false);
        userRepository.save(user);
        emailVerificationService.createVerifactionToken(user);
    }

  
   @Override
public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new NotFoundException("User Not Found"));

    if (!user.isEnabled()) {
        auditLogService.log(
            AuditEventType.LOGIN_BLOCKED,
            user.getEmail(),
            RequestContext.getIp(),
            "/api/auth/login",
            false
        );
        throw new UnauthorizedException("Account not verified");
    }

    if (isAccountLocked(user)) {
        auditLogService.log(
            AuditEventType.LOGIN_BLOCKED,
            user.getEmail(),
            RequestContext.getIp(),
            "/api/auth/login",
            false
        );
        throw new UnauthorizedException("Account is locked. Try again later");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        increaseFailedAttempts(user);
        auditLogService.log(
            AuditEventType.LOGIN_FAILED,
            user.getEmail(),
            RequestContext.getIp(),
            "/api/auth/login",
            false
        );
        throw new UnauthorizedException("Invalid credentials");
    }

    resetFailedAttempts(user);

    String accessToken = jjwtService.generateToken(user.getEmail());
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

    auditLogService.log(
        AuditEventType.LOGIN_SUCCESS,
        user.getEmail(),
        RequestContext.getIp(),
        "/api/auth/login",
        true
    );

    return new AuthResponse(accessToken, refreshToken.getToken());
}

    public void logout(String refreshToken){
    refreshTokenService.logout(refreshToken);
    auditLogService.log(
        AuditEventType.LOGOUT,
        null,
        RequestContext.getIp(),
        "api/auth/logout",
        true
    );
   }

   private boolean isAccountLocked(User user){
    if(user.getLockTime() == null) return false;
    return user.getLockTime()
             .plus(SecurityConstants.LOCK_TIME_DURATION)
             .isAfter(LocalDateTime.now());
   }

   private void lockAccount(User user){
    user.setLockTime((LocalDateTime.now()));
    userRepository.save(user);
   }

   private void resetFailedAttempts(User user) {
    user.setFailedAttempts(0);
    user.setLockTime(null);
    userRepository.save(user);
   }

   private void increaseFailedAttempts(User user) {
    int attempts = user.getFailedAttempts() + 1;
    user.setFailedAttempts(attempts);

    if (attempts >= SecurityConstants.MAX_FAILED_ATTEMPTS) {
        user.setLockTime(LocalDateTime.now());
        auditLogService.log(
            AuditEventType.ACCOUNT_LOCKED,
            user.getEmail(),
            RequestContext.getIp(),
            "/api/auth/login",
            false

        );
    }

    userRepository.saveAndFlush(user); 
}


}
