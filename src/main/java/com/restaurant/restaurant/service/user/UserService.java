package com.restaurant.restaurant.service.user;

import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserService {

    User register(User user);

    User update(User user);

    List<User> getAll();

    User findByLogin(String username);

    User findById(Long id);

    void delete(Long id);
}
