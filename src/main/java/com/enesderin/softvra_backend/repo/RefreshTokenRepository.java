package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken ,Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
