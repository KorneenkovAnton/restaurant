package com.restaurant.restaurant.security.jwt;

import com.restaurant.restaurant.entity.User;

/**
 * Created by Антон on 09.01.2020.
 */
public class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getLastname(),
                user.getAddress(),
                user.getPhonenumber(),
                user.getRole()
        );
    }
}
