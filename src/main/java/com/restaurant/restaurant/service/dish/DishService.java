package com.restaurant.restaurant.service.dish;

import com.restaurant.restaurant.entity.Dish;
import com.restaurant.restaurant.entity.DishType;
import com.restaurant.restaurant.service.Service;

import java.util.List;

/**
 * Created by Антон on 19.01.2020.
 */
public interface DishService extends Service<Dish> {
    List<Dish> findByType(String type);
    List<DishType> getAllTypes();
}
