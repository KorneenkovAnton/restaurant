package com.restaurant.restaurant.service.user;

import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.Service;


public interface UserService extends Service<User> {

    User findByLogin(String username);

}
