package com.neeraj.auth.authservice.ratelimit;

import java.time.Duration;

public class RateLimitPolicies {
    public static final RateLimitPolicy LOGIN =
         new RateLimitPolicy(5,Duration.ofMinutes(1));

    public static final RateLimitPolicy REGISTER =
         new RateLimitPolicy(3, Duration.ofMinutes(10));
         
    public static final RateLimitPolicy FORGOT_PASSWORD =
         new RateLimitPolicy(3,Duration.ofMinutes(15));     

}
