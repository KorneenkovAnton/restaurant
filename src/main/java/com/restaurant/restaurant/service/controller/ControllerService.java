package com.restaurant.restaurant.service.controller;

import com.restaurant.restaurant.dto.AuthenticationRequestDto;
import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.entity.User;

import java.util.Map;

/**
 * Created by Антон on 10.01.2020.
 */
public interface ControllerService {
    User convertUser(RegistrationRequestDto requestDto);
    User convertUser(AuthenticationRequestDto requestDto);
    Map<Object,Object> generateTokens(User user);
}
