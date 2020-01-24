package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.User;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Антон on 09.01.2020.
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getByUserIs(User user);
}
