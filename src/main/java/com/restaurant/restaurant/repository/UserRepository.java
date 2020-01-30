package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User set password = :newPassword where email = :email")
    int updatePasswordByUser(@Param("newPassword") String newPassword,@Param("email") String email);
}
