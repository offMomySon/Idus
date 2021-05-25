package com.example.idus.presentation.controller;

import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.example.idus.service.OrderService;
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

    @GetMapping()
    public OrderQueryResponse getOrder(@RequestParam String email) {
        return orderService.getOrder(email);
    }

    @GetMapping("/list")
    public MembersQueryResponse getOrders(@RequestParam(defaultValue = "", value = "email") String emailSubString,
                                          @RequestParam(defaultValue = "", value = "name") String nameSubString,
                                          @RequestParam(defaultValue = "0") long start) {
        return orderService.getMembersOrder(emailSubString, nameSubString, start);
    }
}
