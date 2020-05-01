package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Table;
import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TableRepository extends JpaRepository<Table,Long> {
    Table findByName(String name);
    @Modifying
    @Transactional
    //@Query("update User set password = :newPassword where email = :email")
    @Query("update Table set status = :status,user = :user where name = :name")
    int updateTableStatusAndUserIdByTableName(@Param("status") String status, @Param("user") User user,
                                              @Param("name") String name);
}
