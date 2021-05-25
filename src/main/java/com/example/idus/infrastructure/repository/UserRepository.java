package com.example.idus.infrastructure.repository;

import com.example.idus.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    //    @Query("SELECT orders " +
//            "FROM User user " +
//            "WHERE user.email LIKE '%' ||:email ||'%' ")
    @Query("select user from User user where user.email LIKE CONCAT('%',:email,'%')   ")
    Page<User> findByEmailContaining(@Param("email") String email, Pageable pageable);

    @Query("select user from User user where user.name LIKE CONCAT('%',:name,'%')   ")
    Page<User> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Query("select user from User user where user.email LIKE CONCAT('%',:email,'%') AND user.name LIKE CONCAT('%',:name,'%')  ")
    Page<User> findByNameAndEmailContaining(@Param("email") String email, @Param("name") String name, Pageable pageable);


//
//    @Query("select user from User user ")
//    Page<User> findByEmailContaining(@Param("email") long email, Pageable pageable);

//    @Query("select c from Customer c where c.lastName LIKE :lastname||'%'")
//    List<Customer> findCustomByLastName( @Param("lastname") String lastName);
    //SELECT e FROM Employee e WHERE e.dept LIKE '%@_%'
//    emailSubString, nameSubString
}
