package com.restaurant.restaurant.service;

import java.util.List;

/**
 * Created by Антон on 19.01.2020.
 */
public interface Service<T> {

    T save(T t);

    T update(T t);

    T findById(Long id);

    List<T> getAll();

    void delete(Long id);

}
