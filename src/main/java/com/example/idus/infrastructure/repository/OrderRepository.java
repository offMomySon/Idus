package com.example.idus.infrastructure.repository;

import com.example.idus.domain.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT orders " +
            "FROM Order orders " +
            "WHERE orders.user.id = :userId")
    List<Order> findByUserId(@Param("userId") long userId);

    @Query("SELECT orders " +
            "FROM Order orders " +
            "WHERE orders.user.id = :userId")
    Optional<Order> findByUserId(@Param("userId") long userId, Sort sort);
}