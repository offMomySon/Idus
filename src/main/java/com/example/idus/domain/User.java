package com.example.idus.domain;

import com.example.idus.infrastructure.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true, updatable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Size(min = 10)
    @Column(nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private Long phoneNumber;

    @Column(length = 100, nullable = false)
    private String email;

    private Gender gender;

    @Builder
    public User(String name, String nickname, String password, Long phoneNumber, String email, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }
}
