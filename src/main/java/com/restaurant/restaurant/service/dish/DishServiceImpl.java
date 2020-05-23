package com.restaurant.restaurant.service.dish;

import com.restaurant.restaurant.entity.Dish;
import com.restaurant.restaurant.entity.DishType;
import com.restaurant.restaurant.repository.DishRepository;
import com.restaurant.restaurant.repository.DishTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Антон on 19.01.2020.
 */
@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishTypeRepository dishTypeRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, DishTypeRepository dishTypeRepository) {
        this.dishRepository = dishRepository;
        this.dishTypeRepository = dishTypeRepository;
    }

    @Override
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish update(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> findByType(String type) {
        return dishRepository.getDishesByTypeName(type);
    }

    @Override
    public List<DishType> getAllTypes() {
        return dishTypeRepository.findAll();
    }

    @Override
    public DishType saveDishType(DishType type) {
        return dishTypeRepository.save(type);
    }
}
