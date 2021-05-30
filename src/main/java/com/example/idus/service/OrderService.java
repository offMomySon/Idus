package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.OrderInfo;
import com.example.idus.presentation.dto.response.MemberInfo;
import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderService {
    public static final long SELECT_PAGE_COUNT = 4;

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

    public MembersQueryResponse getMembersOrder(String emailSubString, String nameSubString, long pageNumber) {
        List<User> users;
        if ("".equals(emailSubString) && "".equals(nameSubString)) {
            users = userRepository.findAll();
        } else if ("".equals(emailSubString)) {
            users = userRepository.findByNameContaining(nameSubString);
        } else if ("".equals(nameSubString)) {
            users = userRepository.findByEmailContaining(emailSubString);
        } else {
            users = userRepository.findByNameAndEmailContaining(emailSubString, nameSubString);
        }

        List<MemberInfo> memberInfos = getMemberInfos(users, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));

        long total = memberInfos.size();
        long totalPages = (int) Math.ceil(total / SELECT_PAGE_COUNT);
        boolean hasNext = pageNumber + 1 < totalPages ? true : false;

        return MembersQueryResponse.builder()
                .items(Lists.newArrayList(
                        memberInfos.subList(
                                (int) (pageNumber * SELECT_PAGE_COUNT),
                                Math.min((int) (pageNumber * SELECT_PAGE_COUNT + SELECT_PAGE_COUNT), memberInfos.size()))
                        )
                )
                .pageCount(SELECT_PAGE_COUNT)
                .totalContent(memberInfos.size())
                .totalPage((long) Math.ceil(total / SELECT_PAGE_COUNT))
                .hasNext(pageNumber + 1 < totalPages)
                .isLast(!hasNext)
                .build();
    }

    private ArrayList<MemberInfo> getMemberInfos(List<User> userPage, PageRequest pageRequest) {
        ArrayList<MemberInfo> memberInfos = new ArrayList<>();

        userPage.forEach(
                user -> orderRepository.findByUserId(user.getId(), pageRequest)
                        .forEach(
                                order -> memberInfos.add(
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