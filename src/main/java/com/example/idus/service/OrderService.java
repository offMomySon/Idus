package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.OrderInfo;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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