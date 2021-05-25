package com.example.idus.presentation.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("로그아웃 요청")
public class LogoutRequest {
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiJ9.eyJ2YWx1ZSI6IjkxYTZkNDJjNmIwOTRjYzdiYTAzNzM0YjI0N2JmYmM3IiwiaWF0IjoxNjE4MTQ5NTMxLCJleHAiOjE2MTkzNTkxMzF9.4MsCnuTs5ep_mE-z63JI5PQ4TYaI24DALmRhovKNm6A")
    @NotBlank
    private String refreshToken;
}
