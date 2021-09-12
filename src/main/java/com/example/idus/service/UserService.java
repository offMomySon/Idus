package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.exception.ErrorCode;
import com.example.idus.infrastructure.exception.list.BusinessException;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.OrderItemQuery;
import com.example.idus.presentation.dto.response.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService {
    public static final long PAGE_COUNT = 7;

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

    public OrderQueryResponse getUserOrders(String email) {


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

//    public MembersQueryResponse getUsers(String emailSubString, String nameSubString, long pageNumber) {
//        List<User> users = getUsers(emailSubString, nameSubString);
//
//        List<OrderItemQuery> memberInfos = getMemberInfos(users);
//
//        long total = memberInfos.size();
//        long totalPages = (int) Math.ceil(total / PAGE_COUNT);
//        boolean hasNext = pageNumber + 1 < totalPages;
//
//        return MembersQueryResponse.builder()
//                .items(Lists.newArrayList(
//                        memberInfos.subList(
//                                (int) (pageNumber * PAGE_COUNT),
//                                Math.min((int) (pageNumber * PAGE_COUNT + PAGE_COUNT), memberInfos.size()))
//                        )
//                )
//                .pageCount(PAGE_COUNT)
//                .totalContent(memberInfos.size())
//                .totalPage((long) Math.ceil(total / PAGE_COUNT))
//                .hasNext(pageNumber + 1 < totalPages)
//                .isLast(!hasNext)
//                .build();
//    }

    public MembersQueryResponse getUsers(String emailSubString, String nameSubString, long pageNumber) {
        List<OrderItemQuery> orderItemQueries = userRepository.findTopByUserOrderByOrderDateDesc(nameSubString);

        long total = orderItemQueries.size();
        long totalPages = (int) Math.ceil(total / PAGE_COUNT);
        boolean hasNext = pageNumber + 1 < totalPages;

        return MembersQueryResponse.builder()
                .items(Lists.newArrayList(
                        orderItemQueries.subList(
                                (int) (pageNumber * PAGE_COUNT),
                                Math.min((int) (pageNumber * PAGE_COUNT + PAGE_COUNT), orderItemQueries.size()))
                        )
                )
                .pageCount(PAGE_COUNT)
                .totalContent(orderItemQueries.size())
                .totalPage((long) Math.ceil(total / PAGE_COUNT))
                .hasNext(pageNumber + 1 < totalPages)
                .isLast(!hasNext)
                .build();
    }

    public MembersQueryResponse testGetUsers(String emailSubString, String nameSubString, long pageNumber) {
        List<OrderItemQuery> orderItemQueries = userRepository.findTopByUserOrderByOrderDateDesc(nameSubString);

        long total = orderItemQueries.size();
        long totalPages = (int) Math.ceil(total / PAGE_COUNT);
        boolean hasNext = pageNumber + 1 < totalPages;

        return MembersQueryResponse.builder()
                .items(Lists.newArrayList(
                        orderItemQueries.subList(
                                (int) (pageNumber * PAGE_COUNT),
                                Math.min((int) (pageNumber * PAGE_COUNT + PAGE_COUNT), orderItemQueries.size()))
                        )
                )
                .pageCount(PAGE_COUNT)
                .totalContent(orderItemQueries.size())
                .totalPage((long) Math.ceil(total / PAGE_COUNT))
                .hasNext(pageNumber + 1 < totalPages)
                .isLast(!hasNext)
                .build();
    }

    private List<User> getUsers(String emailSubString, String nameSubString) {
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
        return users;
    }

    private List<MemberInfo> getMemberInfos(List<User> users) {
        return users.stream()
                .map(user -> orderRepository.findTopByUserOrderByOrderDateDesc(user)
                        .map(order -> MemberInfo
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
                                .build())
                        .orElseGet(() -> MemberInfo.builder()
                                .email(user.getEmail())
                                .name(user.getName())
                                .orderInfo(
                                        OrderInfo.builder()
                                                .orderNumber(null)
                                                .orderDate(null)
                                                .itemName(null)
                                                .build()
                                )
                                .build()))
                .collect(Collectors.toList());
    }
}
