package com.example.idus.infrastructure.repository;

import com.example.idus.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT orders " +
            "FROM Order orders " +
            "WHERE orders.user.id = :userId")
    List<Order> findByUserId(@Param("userId") long userId);

    @Query("SELECT orders " +
            "FROM Order orders " +
            "WHERE orders.user.id = :userId")
    List<Order> findByUserId(@Param("userId") long userId, Pageable pageable);


    @Query("select orders " +
            "from Order orders " +
            "join orders.user user " +
            "where user.email LIKE CONCAT('%',:email,'%')   ")
    Page<Order> findByEmailContaining(@Param("email") String email, Pageable pageable);

    @Query("select orders " +
            "from Order orders " +
            "join orders.user user " +
            "where user.name LIKE CONCAT('%',:name,'%')   ")
    Page<Order> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Query("select orders " +
            "from Order orders " +
            "join orders.user user " +
            "where user.email LIKE CONCAT('%',:email,'%') AND user.name LIKE CONCAT('%',:name,'%') ")
    Page<Order> findByNameAndEmailContaining(@Param("email") String email, @Param("name") String name, Pageable pageable);

}