package com.neeraj.auth.authservice.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.neeraj.auth.authservice.ratelimit.RateLimitPolicies;
import com.neeraj.auth.authservice.ratelimit.RateLimitService;

@ExtendWith(MockitoExtension.class)
class RateLimitServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOps;

    @InjectMocks
    private RateLimitService rateLimitService;

    @BeforeEach
    void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @Test
    void rate_limit_allows_within_limit() {
        when(valueOps.increment(any())).thenReturn(1L);

        boolean allowed =
            rateLimitService.isAllowed("LOGIN:test", RateLimitPolicies.LOGIN);

        assertTrue(allowed);
    }

    @Test
    void rate_limit_blocks_after_limit() {
        when(valueOps.increment(any())).thenReturn(6L);

        boolean allowed =
            rateLimitService.isAllowed("LOGIN:test", RateLimitPolicies.LOGIN);

        assertFalse(allowed);
    }
}
