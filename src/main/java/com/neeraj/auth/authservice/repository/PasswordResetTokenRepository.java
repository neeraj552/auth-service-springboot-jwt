package com.neeraj.auth.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.auth.authservice.entity.PasswordResetToken;
import com.neeraj.auth.authservice.entity.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
}
