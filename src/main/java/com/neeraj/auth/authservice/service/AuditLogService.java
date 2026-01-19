package com.neeraj.auth.authservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.neeraj.auth.authservice.audit.AuditEventType;
import com.neeraj.auth.authservice.audit.AuditLog;
import com.neeraj.auth.authservice.repository.AuditLogRepository;
import com.neeraj.auth.authservice.util.RequestContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    public void log(
        AuditEventType eventType,
        String email,
        String ip,
        String endpoint,
        boolean success
    ){
        AuditLog log = new AuditLog();
        log.setEventType(eventType);
        log.setEmail(email);
        log.setIpAddress(RequestContext.getIp());
        log.setEndpoint(endpoint);
        log.setSuccess(success);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

}
