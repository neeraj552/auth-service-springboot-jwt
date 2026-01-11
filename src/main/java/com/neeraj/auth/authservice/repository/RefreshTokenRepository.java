package com.neeraj.auth.authservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.neeraj.auth.authservice.entity.RefreshToken;
import com.neeraj.auth.authservice.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);


}
