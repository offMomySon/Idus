package com.example.idus.presentation.dto.response;

import com.example.idus.presentation.dto.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfo {
    private String email;
    private String name;
    private OrderInfo orderInfo;
}
