package com.example.idus.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, nullable = false)
    private String orderNumber;

    @Column(length = 100, nullable = false)
    private String itemName;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Order(String orderNumber, String itemName, LocalDateTime orderDate, User user) {
        this.orderNumber = orderNumber;
        this.itemName = itemName;
        this.orderDate = orderDate;
        this.user = user;
    }
}
