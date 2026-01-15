package com.neeraj.auth.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neeraj.auth.authservice.dto.AuthResponse;
import com.neeraj.auth.authservice.dto.ForgotPasswordRequest;
import com.neeraj.auth.authservice.dto.LoginRequest;
import com.neeraj.auth.authservice.dto.LogoutRequest;
import com.neeraj.auth.authservice.dto.RegisterRequest;
import com.neeraj.auth.authservice.dto.ResetPasswordRequest;
import com.neeraj.auth.authservice.entity.RefreshToken;
import com.neeraj.auth.authservice.response.ApiResponse;
import com.neeraj.auth.authservice.service.AuthService;
import com.neeraj.auth.authservice.service.EmailVerificationService;
import com.neeraj.auth.authservice.service.PasswordResetService;
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
    private final PasswordResetService passwordResetService;
    private final EmailVerificationService emailVerificationService;

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
   @PostMapping("/logout")
   public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest request){
    authService.logout(request.getRefreshToken());
    return ResponseEntity.ok(new ApiResponse<>(true,"Logged out succesfully", null)
);
   }
   @PostMapping("/forgot-password")
   public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
    passwordResetService.createResetToken(request.getEmail());
    return ResponseEntity.ok(
        new ApiResponse<>(true,"Password reset token generated(check console for now)",
        null)
    );
   }
   
   @PostMapping("/reset-password")
   public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request){
                passwordResetService.resetPassword(
                    request.getToken(),
                    request.getNewPassword()
                    );
                    return ResponseEntity.ok(
                        new ApiResponse<>(true,"Password reset successful",null)
                    );
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        emailVerificationService.verifyEmail(token);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Email verified succefully", null)
        );
        
    }

}
