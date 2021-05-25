package com.example.idus.service;

import com.example.idus.domain.Order;
import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.OrderInfo;
import com.example.idus.presentation.dto.response.MemberInfo;
import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderService {
    public static final long SELECT_COUNT_LIMIT = 4;

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

    public MembersQueryResponse getMembersOrder(String emailSubString, String nameSubString, long startPageNum) {
        Page<User> userPage;
        PageRequest pageRequest = PageRequest.of((int) (startPageNum / SELECT_COUNT_LIMIT), (int) SELECT_COUNT_LIMIT);

        if ("".equals(emailSubString) && "".equals(nameSubString)) {
            userPage = userRepository.findAll(pageRequest);
        } else if ("".equals(emailSubString)) {
            userPage = userRepository.findByNameContaining(nameSubString, pageRequest);
        } else if ("".equals(nameSubString)) {
            userPage = userRepository.findByEmailContaining(emailSubString, pageRequest);
        } else {
            userPage = userRepository.findByNameAndEmailContaining(emailSubString, nameSubString, pageRequest);
        }

        Sort.TypedSort<Order> orders = Sort.sort(Order.class);
        Sort sort = orders.by(Order::getOrderDate).descending();

        List<MemberInfo> memberInfos = getMemberInfos(userPage, sort);

        return MembersQueryResponse.builder().items(memberInfos).build();
    }

    private ArrayList<MemberInfo> getMemberInfos(Page<User> userPage, Sort sort) {
        ArrayList<MemberInfo> memberInfos = new ArrayList<>();
        userPage.getContent()
                .forEach(user -> orderRepository.findByUserId(user.getId(), sort)
                        .ifPresent(order -> memberInfos.add(
                                MemberInfo
                                        .builder()
                                        .email(user.getEmail())
                                        .name(user.getName())
                                        .orderInfo(
                                                OrderInfo.builder()
                                                        .orderNumber(order.getOrderNumber())
                                                        .orderDate(order.getOrderDate())
                                                        .itemName(order.getItemName())
                                                        .build()
                                        )
                                        .build()
                        )));
        return memberInfos;
    }
}