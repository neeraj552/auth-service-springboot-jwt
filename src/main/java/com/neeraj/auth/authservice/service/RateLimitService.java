package com.neeraj.auth.authservice.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.neeraj.auth.authservice.util.RateLimitInfo;

@Service
public class RateLimitService {
    private static final int MAX_REQUESTS = 5;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(1);
    private final Map<String, RateLimitInfo> requestMap = new ConcurrentHashMap<>();
    public boolean isAllowed(String key){
        RateLimitInfo info = requestMap.get(key);
        if(info == null){
            requestMap.put(key, new RateLimitInfo());
            return true;
        }
        Instant now = Instant.now();
        if(now.isAfter(info.getWindowStart().plus(WINDOW_DURATION))){
            info.reset();
            return true;
        }
        if(info.getRequestCount() < MAX_REQUESTS){
            info.increment();
            return true;
        }
        return false;
    }


}
