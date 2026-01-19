package com.neeraj.auth.authservice.audit;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditEventType eventType;
    @Column(length = 150)
    private String email;
    @Column(nullable = false)
    private String ipAddress;
    @Column(nullable = false)
    private String endpoint;
    private boolean success;
    @Column(nullable = false)
    private LocalDateTime timestamp;


}
