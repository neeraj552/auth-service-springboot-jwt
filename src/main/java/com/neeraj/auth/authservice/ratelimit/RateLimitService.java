package com.neeraj.auth.authservice.ratelimit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isAllowed(String key, RateLimitPolicy policy) {

        String redisKey = "rate_limit:" + key;

        Long count = redisTemplate.opsForValue().increment(redisKey);
        System.out.println("REDIS COUNT [" + redisKey + "] = " + count);
        if (count != null && count == 1) {
            redisTemplate.expire(redisKey, policy.window());
        }

        return count != null && count <= policy.MAX_REQUESTS();
    }
}


