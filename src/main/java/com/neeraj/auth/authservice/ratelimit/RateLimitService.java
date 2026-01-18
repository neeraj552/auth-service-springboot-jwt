package com.neeraj.auth.authservice.ratelimit;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
@Service
public class RateLimitService {

    private final Map<String, RateLimitInfo> requestMap = new ConcurrentHashMap<>();

    public boolean isAllowed(String key, RateLimitPolicy policy) {

        RateLimitInfo info = requestMap.get(key);

        if (info == null) {
            info = new RateLimitInfo();
            requestMap.put(key, info);
            return true;
        }

        Instant now = Instant.now();

        if (now.isAfter(info.getWindowStart().plus(policy.window()))) {
            info.reset();
            return true;
        }

        if (info.getRequestCount() < policy.MAX_REQUESTS()) {
            info.increment();
            return true;
        }

        return false;
    }
}

