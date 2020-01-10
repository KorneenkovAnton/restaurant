package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Антон on 09.01.2020.
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
}
