package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.jwt.JwtTokenUtil;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenUtil jwtTokenUtil;

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
        String email = "test1@naver.com";
        String password = "Aa$0123456";

        //mocking
        UsernamePasswordAuthenticationToken preToken = new UsernamePasswordAuthenticationToken(email, password);
        UsernamePasswordAuthenticationToken postToken = new UsernamePasswordAuthenticationToken(email, password,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
        given(authenticationManager.authenticate(preToken))
                .willReturn(postToken);
        given(jwtTokenUtil.createJwtAccessToken(postToken))
                .willReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE2MjE3NTc4MjksImV4cCI6MTYyMTc1ODcyOX0.2YtVqaJzaO2w98qSNS-cpmSgP9OFbxVzPoDyAwa_FL0");
        given(jwtTokenUtil.createJwtRefreshToken(postToken))
                .willReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlhdCI6MTYyMTc1NzgyOSwiZXhwIjoxNjIyOTY3NDI5fQ.xmnnnksiq20bVMjPMsIfn4m9h7pmIei4XLEHdkEi3Js");
        given(jwtTokenUtil.getJwtExpirationInMillis())
                .willReturn(15 * 60 * 1000L);

        //when
        LoginResponse loginResponse = userService.login(email, password);

        //then
        assertNotNull(loginResponse.getAccessToken());
        assertNotNull(loginResponse.getRefreshToken());
        assertNotNull(loginResponse.getExpiresAt());
        assertNotNull(loginResponse.getEmail());
    }

}