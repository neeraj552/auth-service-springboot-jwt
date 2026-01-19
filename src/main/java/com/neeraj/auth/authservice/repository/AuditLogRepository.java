package com.neeraj.auth.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.auth.authservice.audit.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {

}
