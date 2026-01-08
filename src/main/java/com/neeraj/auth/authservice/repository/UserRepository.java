package com.neeraj.auth.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neeraj.auth.authservice.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
