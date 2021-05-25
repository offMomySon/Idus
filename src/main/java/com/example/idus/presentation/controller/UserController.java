package com.example.idus.presentation.controller;

import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.example.idus.presentation.dto.response.UserMeResponse;
import com.example.idus.presentation.dto.response.UserResponse;
import com.example.idus.service.UserService;
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

    @GetMapping("/me")
    public UserMeResponse getMe() throws Exception {
        return userService.getMeInfo();
    }

    @GetMapping()
    public UserResponse getUser(@RequestParam String email) {
        return userService.getUserInfo(email);
    }
}
