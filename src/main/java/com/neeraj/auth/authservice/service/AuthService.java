package com.neeraj.auth.authservice.service;

import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    String login(LoginRequest request);

}
