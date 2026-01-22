package com.neeraj.auth.authservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.neeraj.auth.authservice.audit.AuditEventType;
import com.neeraj.auth.authservice.dto.AuthResponse;
import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.entity.RefreshToken;
import com.neeraj.auth.authservice.entity.User;
import com.neeraj.auth.authservice.exception.UnauthorizedException;
import com.neeraj.auth.authservice.repository.RoleRepository;
import com.neeraj.auth.authservice.repository.UserRepository;
import com.neeraj.auth.authservice.util.JwtService;
import com.neeraj.auth.authservice.util.RequestContext;
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private AuthServiceImpl authService;

    @AfterEach
    void cleanup() {
        RequestContext.clear();
    }

    @Test
    void login_success() {
        RequestContext.setIp("127.0.0.1");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encoded");
        user.setEnabled(true);
        user.setFailedAttempts(0);
        user.setRoles(Set.of()); 

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");

        when(userRepository.findByEmail(any()))
            .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
            .thenReturn(true);

        when(jwtService.generateToken(any()))
            .thenReturn("jwt-token");

        when(refreshTokenService.createRefreshToken(any()))
            .thenReturn(refreshToken);

        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("pass");
        AuthResponse response = authService.login(request);
        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());

        verify(auditLogService).log(
            eq(AuditEventType.LOGIN_SUCCESS),
            eq("test@test.com"),
            eq("127.0.0.1"),
            eq("/api/auth/login"),
            eq(true)
        );
    }

    @Test
    void login_invalid_password_throws() {
        RequestContext.setIp("127.0.0.1");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encoded");
        user.setEnabled(true);
        user.setFailedAttempts(0);
        user.setRoles(Set.of()); 

        when(userRepository.findByEmail(any()))
            .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
            .thenReturn(false);

        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("wrong");
        assertThrows(UnauthorizedException.class, () ->
            authService.login(request)
        );

        verify(auditLogService).log(
            eq(AuditEventType.LOGIN_FAILED),
            eq("test@test.com"),
            eq("127.0.0.1"),
            eq("/api/auth/login"),
            eq(false)
        );
    }
}
