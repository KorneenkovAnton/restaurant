package com.restaurant.restaurant.service;

import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Антон on 09.01.2020.
 */

public interface UserService {

    User register(User user);

    void update(User user);

    List<User> getAll();

    User findByLogin(String username);

    User findById(Long id);

    void delete(Long id);
}
