package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.response.UserMeResponse;
import com.example.idus.presentation.dto.response.UserResponse;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final MeService meService;
    private final UserRepository userRepository;

    public UserService(MeService meService, UserRepository userRepository, OrderRepository orderRepository) {
        this.meService = meService;
        this.userRepository = userRepository;
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

    public UserResponse getUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        return UserResponse.builder()
                .email(user.getEmail())
                .gender(user.getGender())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
