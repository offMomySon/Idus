package com.example.idus.domain.redis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash("refreshToken")
public class RefreshToken {
    @Id
    String id;
    String email;

    @Builder
    public RefreshToken(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
