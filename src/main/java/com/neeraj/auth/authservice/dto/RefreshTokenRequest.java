package com.neeraj.auth.authservice.dto;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}