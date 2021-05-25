package com.example.idus.presentation.controller;

import com.example.idus.infrastructure.exception.ErrorResponse;
import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.example.idus.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(value = "단일 회원의 주문 목록 조회 api", notes = "회원의 주문들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "단일 회원의 주문 목록 조회"),
            @ApiResponse(code = 400, message = "Email is not Registered", response = ErrorResponse.class),
    })
    @GetMapping()
    public OrderQueryResponse getOrder(@RequestParam String email) {
        return orderService.getOrder(email);
    }

    @ApiOperation(value = "회원 목록 조회 api", notes = "email, name 으로 검색합니다. \t\nstart 로 page 지정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공시 여러 회원 목록 조회"),
            @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
    })
    @GetMapping("/list")
    public MembersQueryResponse getOrders(@RequestParam(defaultValue = "", value = "email") String emailSubString,
                                          @RequestParam(defaultValue = "", value = "name") String nameSubString,
                                          @RequestParam(defaultValue = "0") long start) {
        return orderService.getMembersOrder(emailSubString, nameSubString, start);
    }
}
