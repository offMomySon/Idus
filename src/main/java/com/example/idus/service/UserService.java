package com.example.idus.service;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.repository.UserRepository;
import com.example.idus.presentation.dto.UserSignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public void signup(UserSignupRequest userSignupRequest) {
        User user = userSignupRequest.toEntity();

        userRepository.save(user);
    }

}
