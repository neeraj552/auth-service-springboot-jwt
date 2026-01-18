package com.neeraj.auth.authservice.security;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neeraj.auth.authservice.ratelimit.RateLimitPolicies;
import com.neeraj.auth.authservice.ratelimit.RateLimitService;
import com.neeraj.auth.authservice.response.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter{
    private final RateLimitService rateLimitService;
    private final ObjectMapper objectMapper = new ObjectMapper();

   @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    String path = request.getRequestURI();
    String ip = request.getRemoteAddr();

    boolean allowed = true;

    if (path.equals("/api/auth/login")) {
        allowed = rateLimitService.isAllowed(
            "LOGIN:" + ip,
            RateLimitPolicies.LOGIN
        );
    }

    else if (path.equals("/api/auth/register")) {
        allowed = rateLimitService.isAllowed(
            "REGISTER:" + ip,
            RateLimitPolicies.REGISTER
        );
    }

    else if (path.equals("/api/auth/forgot-password")) {
        String email = request.getParameter("email"); // or extract from body
        allowed = rateLimitService.isAllowed(
            "FORGOT:" + email,
            RateLimitPolicies.FORGOT_PASSWORD
        );
    }

    if (!allowed) {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");

        ApiResponse<?> body =
            new ApiResponse<>(false, "Too many requests. Try again later", null);

        response.getWriter().write(objectMapper.writeValueAsString(body));
        return;
    }

    filterChain.doFilter(request, response);
}


}
