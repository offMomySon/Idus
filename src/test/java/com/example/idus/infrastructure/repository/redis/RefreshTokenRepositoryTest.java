package com.example.idus.infrastructure.repository.redis;

import com.example.idus.domain.redis.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RefreshTokenRepositoryTest {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    RedisService redisService;

    @DisplayName("save and delete refresh token test")
    @Test
    void saveAndDeleteRefreshToken() {
        //given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlhdCI6MTYyMTc1NzgyOSwiZXhwIjoxNjIyOTY3NDI5fQ.xmnnnksiq20bVMjPMsIfn4m9h7pmIei4XLEHdkEi3Js";
        String email = "test1@naver.com";

        RefreshToken refreshToken = RefreshToken.builder()
                .id(token)
                .email(email)
                .build();

        //when
        refreshTokenRepository.save(refreshToken);

        //then
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(token);

        assertThat(findRefreshToken.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findRefreshToken.get().getEmail()).isEqualTo(email);

        //when
        refreshTokenRepository.delete(refreshToken);
        //then
        Optional<RefreshToken> nextFindRefreshToken = refreshTokenRepository.findById(token);
        assertThat(nextFindRefreshToken.isPresent()).isEqualTo(Boolean.FALSE);
    }
}