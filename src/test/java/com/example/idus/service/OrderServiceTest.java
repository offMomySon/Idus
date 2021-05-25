package com.example.idus.service;

import com.example.idus.domain.Order;
import com.example.idus.domain.User;
import com.example.idus.infrastructure.repository.OrderRepository;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.response.OrderQueryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
}