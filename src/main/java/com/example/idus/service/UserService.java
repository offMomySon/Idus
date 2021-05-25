package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.OrderInfo;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.example.idus.presentation.dto.response.UserMeResponse;
import com.example.idus.presentation.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final MeService meService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(MeService meService, UserRepository userRepository, OrderRepository orderRepository) {
        this.meService = meService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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

    public OrderQueryResponse getOrder(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        List<OrderInfo> orderInfos = orderRepository.findByUserId(user.getId())
                .stream()
                .map(order -> OrderInfo.builder()
                        .orderDate(order.getOrderDate())
                        .orderNumber(order.getOrderNumber())
                        .itemName(order.getItemName())
                        .build())
                .collect(Collectors.toList());

        return OrderQueryResponse.builder().items(orderInfos).build();
    }
}
