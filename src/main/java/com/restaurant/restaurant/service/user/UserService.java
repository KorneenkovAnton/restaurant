package com.restaurant.restaurant.service.user;

import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.Service;


public interface UserService extends Service<User> {

    String updatePassword(String oldPassword,String newPassword,String username);
    User findByLogin(String username);

}
