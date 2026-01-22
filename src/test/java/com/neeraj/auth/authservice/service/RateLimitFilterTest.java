package com.neeraj.auth.authservice.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.auth.authservice.audit.AuditEventType;
import com.neeraj.auth.authservice.ratelimit.RateLimitService;
import com.neeraj.auth.authservice.security.RateLimitFilter;
@ExtendWith(MockitoExtension.class)
class RateLimitFilterTest {

    @Mock
    private RateLimitService rateLimitService;

    @Mock
    private AuditLogService auditLogService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        RateLimitFilter filter =
                new RateLimitFilter(rateLimitService, auditLogService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new DummyController())
                .addFilter(filter)
                .build();
    }

    @Test
    void blocked_when_rate_limited() throws Exception {
        when(rateLimitService.isAllowed(any(), any()))
                .thenReturn(false);

        mockMvc.perform(post("/api/auth/login"))
               .andExpect(status().isTooManyRequests());

        verify(auditLogService).log(
                eq(AuditEventType.RATE_LIMIT_BLOCKED),
                isNull(),
                any(),
                eq("/api/auth/login"),
                eq(false)
        );
    }

    @RestController
    static class DummyController {
        @PostMapping("/api/auth/login")
        public void login() {}
    }
}
