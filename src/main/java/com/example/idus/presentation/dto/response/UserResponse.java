package com.example.idus.presentation.dto.response;

import com.example.idus.infrastructure.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String nickname;
    private Long phoneNumber;
    private String email;
    private Gender gender;

    @Builder
    public UserResponse(String name, String nickname, Long phoneNumber, String email, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }
}