package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
