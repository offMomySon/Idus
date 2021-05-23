package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.domain.redis.RefreshToken;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.InvalidValueException;
import com.example.idus.infrastructure.jwt.JwtTokenUtil;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.infrastructure.repository.redis.RefreshTokenRepository;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.LoginResponse;
import com.example.idus.presentation.dto.response.LogoutResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, RefreshTokenRepository refreshTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public void signup(SignupRequest signupRequest) {
        checkRegisteredEmail(signupRequest);

        User user = signupRequest.toEntity(passwordEncoder);

        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenUtil.createJwtAccessToken(authentication);
        String refreshToken = jwtTokenUtil.createJwtRefreshToken(authentication);

        refreshTokenRepository.save(RefreshToken.builder().id(refreshToken).email(email).build());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresAt(Instant.now().plusMillis(jwtTokenUtil.getAccessJwtExpirationInMillis()))
                .email(email)
                .build();
    }

    @Transactional
    public LogoutResponse logout(String refreshToken) {
        deleteRefreshToken(refreshToken);

        return LogoutResponse.builder()
                .success(TRUE)
                .logoutTime(LocalDateTime.now())
                .build();
    }

    private void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findById(refreshToken).ifPresent(foundRefreshToken -> {
            refreshTokenRepository.delete(foundRefreshToken);
        });
    }

    private void checkRegisteredEmail(SignupRequest signupRequest) {
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(user -> {
            throw new InvalidValueException(signupRequest.getEmail(), ErrorCode.EMAIL_DUPLICATION);
        });
    }
}
