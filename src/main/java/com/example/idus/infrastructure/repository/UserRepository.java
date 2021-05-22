package com.example.idus.infrastructure.repository;

import com.example.idus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
