package com.neeraj.auth.authservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.auth.authservice.audit.AuditEventType;
import com.neeraj.auth.authservice.repository.AuditLogRepository;
import com.neeraj.auth.authservice.response.ApiResponse;
import com.neeraj.auth.authservice.service.AuditLogService;
import com.neeraj.auth.authservice.util.RequestContext;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogService auditLogService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<?> adminDashboard() {

        auditLogService.log(
            AuditEventType.ACCESS_ADMIN_DASHBOARD,
            SecurityContextHolder.getContext().getAuthentication().getName(),
            RequestContext.getIp(),
            "/api/admin/dashboard",
            true
        );

        return new ApiResponse<>(
            true,
            "Welcome ADMIN! This is admin dashboard.",
            null
        );
    }

    @GetMapping("/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<?> getAuditLogs() {

        auditLogService.log(
            AuditEventType.AUDIT_LOG_VIEWED,
            SecurityContextHolder.getContext().getAuthentication().getName(),
            RequestContext.getIp(),
            "/api/admin/audit-logs",
            true
        );

        return new ApiResponse<>(
            true,
            "Audit logs fetched successfully",
            auditLogRepository.findAll()
        );
    }
}
