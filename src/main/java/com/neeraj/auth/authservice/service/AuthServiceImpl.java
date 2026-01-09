package com.neeraj.auth.authservice.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.dto.RegisterRequest;
import com.neeraj.auth.authservice.entity.Role;
import com.neeraj.auth.authservice.entity.User;
import com.neeraj.auth.authservice.exception.BadRequestException;
import com.neeraj.auth.authservice.exception.NotFoundException;
import com.neeraj.auth.authservice.exception.UnauthorizedException;
import com.neeraj.auth.authservice.repository.RoleRepository;
import com.neeraj.auth.authservice.repository.UserRepository;
import com.neeraj.auth.authservice.util.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jjwtService;

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

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow (() -> new NotFoundException("User Not Found"));
                if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
                    throw new UnauthorizedException("Invalid credentials");
                }

        return jjwtService.generateToken(user.getEmail());
    }
}
