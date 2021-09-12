package com.example.idus.presentation.dto;

import java.time.LocalDateTime;

public interface OrderItemQuery {
    String getEmail();

    String getName();

    String getOrderNumber();

    String getItemName();

    LocalDateTime getOrderDate();
}
