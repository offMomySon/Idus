package com.example.idus.presentation.controller;

import com.example.idus.presentation.dto.UserSignupRequest;
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

    @PostMapping(path = "/signup")
    public String userSignup(@RequestBody @Valid UserSignupRequest userSignupRequest) {

        log.info(userSignupRequest.toString());

        return "redirect:/";
    }
}
