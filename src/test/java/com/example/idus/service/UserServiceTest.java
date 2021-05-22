package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.UserSignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    @DisplayName("Duplicate Email Signup failure Test")
    @Test
    void signup() {
        //given
        UserSignupRequest userSignupRequest = new UserSignupRequest();
        userSignupRequest.setName("testName");
        userSignupRequest.setNickname("testNickName");
        userSignupRequest.setPassword("1aA!111111");
        userSignupRequest.setPhoneNumber("010-7177-1111");
        userSignupRequest.setEmail("test1@naver.com");

        //mocking
        User user = userSignupRequest.toEntity();
        given(userRepository.findByEmail("test1@naver.com"))
                .willReturn(java.util.Optional.ofNullable(user));

        //when
        assertThrows(RuntimeException.class, () -> {
            userService.signup(userSignupRequest);
        });

        //then
        assertTrue(true);
    }
}