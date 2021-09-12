package com.example.idus.infrastructure.repository;

import com.example.idus.domain.User;
import com.example.idus.presentation.dto.OrderItemQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    @Query("select user from User user where user.email LIKE CONCAT('%',:email,'%')   ")
    List<User> findByEmailContaining(@Param("email") String email);

    @Query("select user from User user where user.name LIKE CONCAT('%',:name,'%')   ")
    List<User> findByNameContaining(@Param("name") String name);

    @Query("select user from User user where user.email LIKE CONCAT('%',:email,'%') AND user.name LIKE CONCAT('%',:name,'%')  ")
    List<User> findByNameAndEmailContaining(@Param("email") String email, @Param("name") String name);


    @Query(value =
            "SELECT " +
                    "row_num_table.email as email, " +
                    "row_num_table.name as name, " +
                    "row_num_table.order_number as orderNumber, " +
                    "row_num_table.item_name as itemName, " +
                    "row_num_table.order_date as orderDate " +
                    "from " +
                    "( " +
                    "select serch_user.email, serch_user.name,  orders.order_number, orders.item_name, orders.order_date, row_number() over(partition by serch_user.name order by orders.order_date desc ) as rn " +
                    "from idus.orders  as orders " +
                    "join (select * from idus.user where idus.user.name LIKE CONCAT('%',:name,'%') ) serch_user on serch_user.id = orders.user_id " +
                    "order by orders.id asc " +
                    ") as row_num_table " +
                    "where row_num_table.rn = 1", nativeQuery = true)
    List<OrderItemQuery> findTopByUserOrderByOrderDateDesc(@Param("name") String name);
}
