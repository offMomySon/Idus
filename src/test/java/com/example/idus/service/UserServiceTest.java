package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.domain.redis.RefreshToken;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.jwt.JwtTokenUtil;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.infrastructure.repository.redis.RefreshTokenRepository;
import com.example.idus.presentation.dto.request.LogoutRequest;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.LoginResponse;
import com.example.idus.presentation.dto.response.LogoutResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @DisplayName("Duplicate Email Signup failure Test")
    @Test
    void signupFailAtDuplicateEmail() {
        //given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testName");
        signupRequest.setNickname("testNickName");
        signupRequest.setPassword("1aA!111111");
        signupRequest.setPhoneNumber("010-7177-1111");
        signupRequest.setEmail("test1@naver.com");

        //mocking
        User user = signupRequest.toEntity(passwordEncoder);
        given(userRepository.findByEmail("test1@naver.com"))
                .willReturn(java.util.Optional.ofNullable(user));

        //when
        assertThrows(BusinessException.class, () -> {
            userService.signup(signupRequest);
        });

        //then
        assertTrue(true);
    }

    @Test
    void successLogin() {
        //given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testName");
        signupRequest.setNickname("testNickName");
        signupRequest.setPassword("1aA!111111");
        signupRequest.setPhoneNumber("010-7177-1111");
        signupRequest.setEmail("testfor123_testfor123_testfor123@naver.com");

        userService.signup(signupRequest);

        //when
        LoginResponse loginResponse = userService.login(signupRequest.getEmail(), signupRequest.getPassword());

        //then
        assertNotNull(loginResponse.getAccessToken());
        assertNotNull(loginResponse.getRefreshToken());
        assertNotNull(loginResponse.getExpiresAt());
        assertNotNull(loginResponse.getEmail());
    }

    @Test
    void successlogout() {
        //given
        RefreshToken refreshToken = RefreshToken.builder()
                .id("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlhdCI6MTYyMTc1NzgyOSwiZXhwIjoxNjIyOTY3NDI5fQ.xmnnnksiq20bVMjPMsIfn4m9h7pmIei4XLEHdkEi3Js")
                .email("test1@naver.com")
                .build();
        refreshTokenRepository.save(refreshToken);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlhdCI6MTYyMTc1NzgyOSwiZXhwIjoxNjIyOTY3NDI5fQ.xmnnnksiq20bVMjPMsIfn4m9h7pmIei4XLEHdkEi3Js");

        //when
        LogoutResponse logoutResponse = userService.logout(logoutRequest.getRefreshToken());

        //then
        assertThat(logoutResponse.getSuccess()).isEqualTo(Boolean.TRUE);
    }
}