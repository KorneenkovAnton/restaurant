package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Антон on 20.01.2020.
 */
public interface DishTypeRepository extends JpaRepository<DishType,Long> {
}
