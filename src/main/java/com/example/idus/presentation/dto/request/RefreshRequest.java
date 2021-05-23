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
@ApiModel("Access token 재생성 요청")
public class RefreshRequest {
    @NotBlank
    @ApiModelProperty(example = "2d7434ed2d0c45419dbbf7d488ef63bb")
    public String email;
    @NotBlank
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTAtNzE3My0xNDAzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYxODU3NTQ3MiwiZXhwIjoxNjE4NTc3MjcyfQ.2lXF1ym1xISevH3cnCE4AOhO09zLpNNSSQl4ainXR6I")
    public String refreshToken;
}
