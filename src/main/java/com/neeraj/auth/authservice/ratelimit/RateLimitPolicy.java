package com.neeraj.auth.authservice.ratelimit;

import java.time.Duration;

public record RateLimitPolicy(
    int MAX_REQUESTS,
    Duration window
) {}
