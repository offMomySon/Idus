package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.presentation.dto.request.SignupRequest;
import com.example.idus.presentation.dto.response.UserMeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @MockBean
    MeService meService;
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
}