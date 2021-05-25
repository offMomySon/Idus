package com.example.idus.presentation.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("로그인 응답")
public class LoginResponse {
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlhdCI6MTYyMTc1NzgyOSwiZXhwIjoxNjIyOTY3NDI5fQ.xmnnnksiq20bVMjPMsIfn4m9h7pmIei4XLEHdkEi3Js")
    private String accessToken;
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE2MjE3NTc4MjksImV4cCI6MTYyMTc1ODcyOX0.2YtVqaJzaO2w98qSNS-cpmSgP9OFbxVzPoDyAwa_FL0")
    private String refreshToken;
    @ApiModelProperty(example = "test@naver.com")
    private String email;
    @ApiModelProperty(example = "2021-05-23T08:32:09.113618600Z")
    private Instant expiresAt;
}
