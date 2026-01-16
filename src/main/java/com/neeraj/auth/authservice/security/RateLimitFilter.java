package com.neeraj.auth.authservice.security;

import java.io.IOException;
import java.util.logging.Filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.neeraj.auth.authservice.response.ApiResponse;
import com.neeraj.auth.authservice.service.RateLimitService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter{
    private final RateLimitService rateLimitService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{
        String path = request.getRequestURI();
        if(path.startsWith("/api/auth/login")
            || path.startsWith("/api/auth/forgot-password")
            || path.startsWith("/api/auth/register")){
                String ip = request.getRemoteAddr();
                String key = ip + ":" + path;

                if(!rateLimitService.isAllowed(key)){
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setContentType("application/json");

                    ApiResponse<?> body =
                            new ApiResponse<>(false, "Too many request. Try again later", null);
                    response.getWriter()
                                    .write(objectMapper.writeValueAsString(body));
                    return;
                }

            }

            filterChain.doFilter(request, response);
        
    }

}
