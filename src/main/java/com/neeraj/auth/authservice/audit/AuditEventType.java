package com.neeraj.auth.authservice.audit;

public enum AuditEventType {
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    LOGIN_BLOCKED,
    ACCOUNT_LOCKED,
    RATE_LIMIT_BLOCKED,
    PASSWORD_RESET_REQUESTED,
    PASSWORD_RESET_COMPLETED,
    LOGOUT

}
