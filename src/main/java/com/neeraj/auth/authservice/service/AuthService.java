package com.neeraj.auth.authservice.service;

import com.neeraj.auth.authservice.dto.AuthResponse;
import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String refreshToken);

}
