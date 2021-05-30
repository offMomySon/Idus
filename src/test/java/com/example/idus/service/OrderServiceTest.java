package com.example.idus.service;

import com.example.idus.domain.Order;
import com.example.idus.domain.User;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.response.MembersQueryResponse;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @MockBean
    MeService meService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

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
        OrderQueryResponse orderQueryResponse = orderService.getOrder(email);

        //then
        assertThat(orderQueryResponse.getItems().size()).isEqualTo(2);
    }

    @Test
    void teatGetMembersOrder() {
        //given

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plusOneDays = LocalDateTime.now().plusDays(1);
        LocalDateTime plusTwoDays = LocalDateTime.now().plusDays(2);

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

        //when
        String findEmail = "";
        String findName = "testName";
        long start = 2;

        MembersQueryResponse membersOrder = orderService.getMembersOrder(findEmail, findName, start);

        //then
        Assertions.assertEquals(plusTwoDays, membersOrder.getItems().get(0).getOrderInfo().getOrderDate());
        Assertions.assertTrue(membersOrder.getItems().get(0).getName().contains(findName));
    }
}