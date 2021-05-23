package com.example.idus.presentation.controller;

import com.example.idus.infrastructure.exception.ErrorResponse;
import com.example.idus.presentation.dto.request.LoginRequest;
import com.example.idus.presentation.dto.request.LogoutRequest;
import com.example.idus.presentation.dto.request.RefreshRequest;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.LoginResponse;
import com.example.idus.presentation.dto.response.LogoutResponse;
import com.example.idus.presentation.dto.response.RefreshResponse;
import com.example.idus.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "유저 등록", notes = "성공시 유저등록.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success, User sign up."),
            @ApiResponse(code = 400, message = "Fail, Email is Duplication.", response = ErrorResponse.class),
    })
    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest signupRequest) {
        authService.signup(signupRequest);

        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        return authService.login(email, password);
    }

    @PostMapping(path = "/logout")
    public LogoutResponse logout(@RequestBody @Valid LogoutRequest logoutRequest) {
        String refreshToken = logoutRequest.getRefreshToken();

        return authService.logout(refreshToken);
    }

    @PostMapping(path = "/token")
    public RefreshResponse refreshToken(@RequestBody @Valid RefreshRequest tokenRequest) {
        String email = tokenRequest.getEmail();
        String refreshToken = tokenRequest.getRefreshToken();

        return authService.createToken(email, refreshToken);
    }
}
