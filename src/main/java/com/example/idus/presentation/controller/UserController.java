package com.example.idus.presentation.controller;

import com.example.idus.infrastructure.exception.ErrorResponse;
import com.example.idus.presentation.dto.UserSignupRequest;
import com.example.idus.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "유저 등록", notes = "성공시, home page redirection")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success, User sign up.\t\n Redirection to Home page."),
            @ApiResponse(code = 400, message = "Fail, Email is Duplication.", response = ErrorResponse.class),
    })
    @PostMapping(path = "/signup")
    public String userSignup(@RequestBody @Valid UserSignupRequest userSignupRequest) {
        userService.signup(userSignupRequest);

        return "redirect:/";
    }
}
