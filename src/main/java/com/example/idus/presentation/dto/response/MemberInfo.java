package com.example.idus.presentation.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("멤버정보")
public class MemberInfo {
    @ApiModelProperty(example = "test@naver.com")
    private String email;
    @ApiModelProperty(example = "가힣azAZ")
    private String name;
    private OrderInfo orderInfo;
}
