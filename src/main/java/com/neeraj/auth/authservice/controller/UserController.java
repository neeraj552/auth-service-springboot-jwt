package com.neeraj.auth.authservice.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.auth.authservice.audit.AuditEventType;
import com.neeraj.auth.authservice.entity.User;
import com.neeraj.auth.authservice.exception.NotFoundException;
import com.neeraj.auth.authservice.repository.UserRepository;
import com.neeraj.auth.authservice.response.ApiResponse;
import com.neeraj.auth.authservice.service.AuditLogService;
import com.neeraj.auth.authservice.util.RequestContext;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<?> userDashboard() {

        auditLogService.log(
            AuditEventType.ACCESS_USER_DASHBOARD,
            SecurityContextHolder.getContext().getAuthentication().getName(),
            RequestContext.getIp(),
            "/api/user/dashboard",
            true
        );

        return new ApiResponse<>(
            true,
            "Welcome USER! This is your dashboard.",
            null
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<?> me() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        auditLogService.log(
            AuditEventType.TOKEN_ACCESS,
            email,
            RequestContext.getIp(),
            "/api/user/me",
            true
        );

        return new ApiResponse<>(
            true,
            "Authenticated user details",
            Map.of(
                "email", user.getEmail(),
                "roles", user.getRoles(),
                "enabled", user.isEnabled()
            )
        );
    }
}
