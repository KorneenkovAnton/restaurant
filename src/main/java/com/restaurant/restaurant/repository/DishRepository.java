package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Dish;
import com.restaurant.restaurant.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Антон on 09.01.2020.
 */
public interface DishRepository extends JpaRepository<Dish,Long> {
    List<Dish> getDishesByTypeName(String type);
}

