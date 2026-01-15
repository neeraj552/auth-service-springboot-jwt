package com.neeraj.auth.authservice.util;

import java.time.Duration;

public class SecurityConstants {
    public static final int MAX_FAILED_ATTEMPTS = 5;
    public static final Duration LOCK_TIME_DURATION = Duration.ofMinutes(15);
    private SecurityConstants(){}

}
