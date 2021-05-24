package com.example.idus.presentation.dto.response;

import com.example.idus.presentation.dto.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderQueryResponse {
    List<OrderInfo> items;
}
