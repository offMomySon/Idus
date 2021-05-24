package com.example.idus.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderInfo {
    private String orderNumber;
    private String itemName;
    private LocalDateTime orderDate;
}
