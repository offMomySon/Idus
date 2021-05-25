package com.example.idus.presentation.controller;

import com.example.idus.infrastructure.exception.ErrorResponse;
import com.example.idus.presentation.dto.response.UserMeResponse;
import com.example.idus.presentation.dto.response.UserResponse;
import com.example.idus.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "내 정보 조회 api", notes = "나의 상세 정보 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "나의 상세 정보 조회"),
    })
    @GetMapping("/me")
    public UserMeResponse getMe() throws Exception {
        return userService.getMeInfo();
    }

    @ApiOperation(value = "유저 정보 조회 api", notes = "회원의 상세 정보 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원의 상세 정보 조회"),
            @ApiResponse(code = 400, message = "Email is not Registered", response = ErrorResponse.class),
    })
    @GetMapping()
    public UserResponse getUser(@RequestParam String email) {
        return userService.getUserInfo(email);
    }
}
