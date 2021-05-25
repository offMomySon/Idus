package com.example.idus.presentation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@ApiModel("주문 정보")
public class OrderInfo {
    @ApiModelProperty(example = "ABCD123")
    private String orderNumber;
    @ApiModelProperty(example = "itemnow")
    private String itemName;
    @ApiModelProperty(example = "2021-05-25T02:39:11")
    private LocalDateTime orderDate;
}
