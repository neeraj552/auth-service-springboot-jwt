package com.neeraj.auth.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.auth.authservice.entity.EmailVerificationToken;
import com.neeraj.auth.authservice.entity.User;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByToken(String Long);
    void deleteByUser(User user);

}
