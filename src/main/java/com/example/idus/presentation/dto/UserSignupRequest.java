package com.example.idus.presentation.dto;

import com.example.idus.domain.User;
import com.example.idus.infrastructure.enums.Gender;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSignupRequest {
    @NotBlank(message = "name is mandatory")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$")
    private String name;

    @NotBlank(message = "nickname is mandatory")
    @Pattern(regexp = "^[a-z]+$")
    private String nickname;

    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{10,}$")
    private String password;

    @NotBlank(message = "phoneNumber is mandatory")
    @Pattern(regexp = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$")
    private String phoneNumber;

    @NotBlank(message = "email is mandatory")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    private Gender gender;

    public User toEntity() {
        return User.builder()
                .name(getName())
                .nickname(getNickname())
                .password(getPassword())
                .phoneNumber(Long.valueOf(getPhoneNumber().replaceAll("-", "")))
                .email(getEmail())
                .gender(getGender())
                .build();
    }
}
