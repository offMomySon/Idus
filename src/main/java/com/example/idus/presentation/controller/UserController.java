package com.example.idus.presentation.controller;

import com.example.idus.infrastructure.exception.ErrorResponse;
import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.example.idus.presentation.dto.response.UserMeResponse;
import com.example.idus.presentation.dto.response.UserResponse;
import com.example.idus.service.UserService;
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
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "내 정보 조회 api", notes = "나의 상세 정보 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "나의 상세 정보 조회"),
    })
    @GetMapping("/user/me")
    public UserMeResponse getMe() throws Exception {
        return userService.getMeInfo();
    }

    // 참조.
    // https://developers.kakao.com/docs/latest/ko/kakaotalk-social/rest-api#get-friends
    @ApiOperation(value = "회원 목록 조회 api", notes = "email, name 으로 검색합니다. \t\nstart 로 page 지정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공시 여러 회원 목록 조회"),
            @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
    })
    @GetMapping("/users")
    public MembersQueryResponse getOrders(@RequestParam(defaultValue = "", value = "email") String emailSubString,
                                          @RequestParam(defaultValue = "", value = "name") String nameSubString,
                                          @RequestParam(defaultValue = "0") long start) {
        return userService.getUsers(emailSubString, nameSubString, start);
    }

    @ApiOperation(value = "유저 정보 조회 api", notes = "회원의 상세 정보 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원의 상세 정보 조회"),
            @ApiResponse(code = 400, message = "Email is not Registered", response = ErrorResponse.class),
    })
    @GetMapping("/users/info")
    public UserResponse getUser(@RequestParam String email) {
        return userService.getUserInfo(email);
    }

    // 참조.
    // https://docs.oracle.com/en/cloud/paas/content-cloud/rest-api-documents/api-users.html
    @ApiOperation(value = "단일 회원의 주문 목록 조회 api", notes = "회원의 주문들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "단일 회원의 주문 목록 조회"),
            @ApiResponse(code = 400, message = "Email is not Registered", response = ErrorResponse.class),
    })
    @GetMapping("/users/search/orders")
    public OrderQueryResponse getOrder(@RequestParam String email) {
        return userService.getUserOrders(email);
    }
}
