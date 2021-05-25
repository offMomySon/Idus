package com.example.idus.presentation.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@ApiModel("Refresh token 재발급 응답")
@Data
@Builder
public class RefreshResponse {
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiJ9.eyJ2YWx1ZSI6IjkxYTZkNDJjNmIwOTRjYzdiYTAzNzM0YjI0N2JmYmM3IiwiaWF0IjoxNjE4MTQ5NTMxLCJleHAiOjE2MTkzNTkxMzF9.4MsCnuTs5ep_mE-z63JI5PQ4TYaI24DALmRhovKNm6A")
    public String refreshToken;
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTAtNzE3My0xNDAzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYxODE0OTUzMSwiZXhwIjoxNjE4MTUxMzMxfQ.RRXxrmaa_1p2Bbz9ndtdKRJ8SsavcwWsKW6TLHA8UUQ")
    public String accessToken;
    @ApiModelProperty(example = "2021-05-23T08:32:09.113618600Z")
    private Instant expiresAt;
}
