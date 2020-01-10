package com.restaurant.restaurant.service.controller;

import com.restaurant.restaurant.dto.AuthenticationRequestDto;
import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.dto.UserDto;
import com.restaurant.restaurant.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ControllerService {
    boolean getUpdateAccess(UserDto requestDto,HttpServletRequest request);
    User convertUser(RegistrationRequestDto requestDto);
    User convertUser(AuthenticationRequestDto requestDto);
    Map<Object,Object> generateTokens(User user);
}
