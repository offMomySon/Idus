package com.example.idus.service;

import com.example.idus.domain.Order;
import com.example.idus.domain.User;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import com.example.idus.presentation.dto.response.UserMeResponse;
import com.example.idus.presentation.dto.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    MeService meService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void successGetMeInfo() throws Exception {
        //given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testName");
        signupRequest.setNickname("testNickName");
        signupRequest.setPassword("1aA!111111");
        signupRequest.setPhoneNumber("010-7177-1111");
        signupRequest.setEmail("test1@naver.com");
        User user = signupRequest.toEntity(passwordEncoder);

        //mocking
        given(meService.getMe())
                .willReturn(user);

        //when
        UserMeResponse userServiceMeInfo = userService.getMeInfo();

        //then
        assertThat(userServiceMeInfo.getEmail()).isEqualTo("test1@naver.com");
    }

    @Test
    @Transactional
    void successGetUserInfo() {
        //given
        String userEmail = UUID.randomUUID().toString().replace("-", "") + "@naver.com";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("testName");
        signupRequest.setNickname("testNickName");
        signupRequest.setPassword("1aA!111111");
        signupRequest.setPhoneNumber("010-7177-1111");
        signupRequest.setEmail(userEmail);
        User user = signupRequest.toEntity(passwordEncoder);

        userRepository.save(user);

        //when
        UserResponse userInfo = userService.getUserInfo(userEmail);

        //then
        assertThat(userInfo.getEmail()).isEqualTo(userEmail);
    }

    @Test
    void successGetOrder() {
        //given
        String email = UUID.randomUUID().toString().replace("-", "") + "@naver.com";
        User user = userRepository.save(
                User.builder()
                        .phoneNumber(177717171L)
                        .gender(null)
                        .nickname("test")
                        .name("test")
                        .email(email)
                        .password(passwordEncoder.encode("1aA!111111"))
                        .build()
        );

        orderRepository.save(
                Order.builder()
                        .orderNumber("TEST1")
                        .orderDate(LocalDateTime.now())
                        .itemName("now")
                        .user(user)
                        .build()
        );
        orderRepository.save(
                Order.builder()
                        .orderNumber("TEST2")
                        .orderDate(LocalDateTime.now())
                        .itemName("now")
                        .user(user)
                        .build()
        );

        //when
        OrderQueryResponse orderQueryResponse = userService.getUserOrders(email);

        //then
        assertThat(orderQueryResponse.getItems().size()).isEqualTo(2);
    }

    @Test
    void teatGetMembersOrder() {
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plusOneDays = LocalDateTime.now().plusDays(1);
        LocalDateTime plusTwoDays = LocalDateTime.now().plusDays(2);

//        registUserAndOrderToDB(now, plusOneDays, plusTwoDays);

        //when
        String findEmail = "";
        String findName = "testName1";
        long start = 0;

        MembersQueryResponse membersOrder = userService.getUsers(findEmail, findName, start);

        //then
        System.out.println("======================");
        System.out.println(membersOrder);
        System.out.println("======================");

//        Assertions.assertEquals(plusTwoDays, membersOrder.getItems().get(0).getOrderInfo().getOrderDate());
        Assertions.assertTrue(membersOrder.getItems().get(0).getName().contains(findName));
    }

    private void registUserAndOrderToDB(LocalDateTime now, LocalDateTime plusOneDays, LocalDateTime plusTwoDays) {
        for (int i = 0; i < 10; i++) {
            String name = "testName" + i;
            String email = "test" + i + "@naver.com";

            User user = userRepository.save(
                    User.builder()
                            .phoneNumber(177717171L)
                            .gender(null)
                            .nickname("test")
                            .name(name)
                            .email(email)
                            .password(passwordEncoder.encode("1aA!111111"))
                            .build()
            );
            orderRepository.save(
                    Order.builder()
                            .orderNumber("orderNum1")
                            .orderDate(now)
                            .itemName("item1")
                            .user(user)
                            .build()
            );
            orderRepository.save(
                    Order.builder()
                            .orderNumber("orderNum2")
                            .orderDate(plusOneDays)
                            .itemName("item2")
                            .user(user)
                            .build()
            );
            orderRepository.save(
                    Order.builder()
                            .orderNumber("orderNum3")
                            .orderDate(plusTwoDays)
                            .itemName("item3")
                            .user(user)
                            .build()
            );
        }
    }
}