package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.presentation.dto.response.UserMeResponse;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final MeService meService;

    public UserService(MeService meService) {
        this.meService = meService;
    }

    public UserMeResponse getMeInfo() throws Exception {
        User me = meService.getMe();

        return UserMeResponse.builder()
                .email(me.getEmail())
                .gender(me.getGender())
                .name(me.getName())
                .nickname(me.getNickname())
                .phoneNumber(me.getPhoneNumber())
                .build();
    }
}
