package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.InvalidValueException;
import com.example.idus.infrastructure.jwt.JwtTokenUtil;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public void signup(SignupRequest signupRequest) {
        checkRegisteredEmail(signupRequest);

        User user = signupRequest.toEntity(passwordEncoder);

        userRepository.save(user);
    }

    public LoginResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return LoginResponse.builder()
                .accessToken(jwtTokenUtil.createJwtAccessToken(authentication))
                .refreshToken(jwtTokenUtil.createJwtRefreshToken(authentication))
                .expiresAt(Instant.now().plusMillis(jwtTokenUtil.getJwtExpirationInMillis()))
                .email(email)
                .build();
    }
    
    private void checkRegisteredEmail(SignupRequest signupRequest) {
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(user -> {
            throw new InvalidValueException(signupRequest.getEmail(), ErrorCode.EMAIL_DUPLICATION);
        });
    }
}
