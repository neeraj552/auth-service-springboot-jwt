package com.neeraj.auth.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.auth.authservice.dto.AuthResponse;
import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.dto.RegisterRequest;
import com.neeraj.auth.authservice.entity.RefreshToken;
import com.neeraj.auth.authservice.response.ApiResponse;
import com.neeraj.auth.authservice.service.AuthService;
import com.neeraj.auth.authservice.service.RefreshTokenService;
import com.neeraj.auth.authservice.util.JwtService;
import com.neeraj.auth.authservice.dto.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(true,"User registered successfully",null));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        AuthResponse result = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", result));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {

    RefreshToken token = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

    String newAccessToken = jwtService.generateToken(token.getUser().getEmail());

    AuthResponse response = new AuthResponse(newAccessToken, request.getRefreshToken());

    return ResponseEntity.ok(new ApiResponse<>(true, "Token refreshed successfully", response));
}


}
