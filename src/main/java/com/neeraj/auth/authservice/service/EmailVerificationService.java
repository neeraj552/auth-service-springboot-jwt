package com.neeraj.auth.authservice.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.neeraj.auth.authservice.exception.BadRequestException;
import org.springframework.stereotype.Service;

import com.neeraj.auth.authservice.entity.EmailVerificationToken;
import com.neeraj.auth.authservice.entity.User;
import com.neeraj.auth.authservice.repository.EmailVerificationTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailVerificationTokenRepository tokenRepository;
    public void createVerifactionToken(User user){
        tokenRepository.deleteByUser(user);
        EmailVerificationToken token = new EmailVerificationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryTime(LocalDateTime.now().plusHours(24));
        tokenRepository.save(token);
        System.out.println("EMAIL VERIFICATION TOKEN:" + token.getToken());
    }

   public void verifyEmail(String tokenValue) {

        EmailVerificationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new BadRequestException("Invalid verification token"));

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification token expired");
        }

        User user = token.getUser();
        user.setEnabled(true);

        tokenRepository.delete(token);
    }
    


}
