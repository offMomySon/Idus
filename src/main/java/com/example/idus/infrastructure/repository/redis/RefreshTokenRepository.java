package com.example.idus.infrastructure.repository.redis;

import com.example.idus.domain.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}