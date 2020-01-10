package com.restaurant.restaurant.service.controller;

import com.restaurant.restaurant.dto.AuthenticationRequestDto;
import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import com.restaurant.restaurant.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Антон on 10.01.2020.
 */
@Service
public class ControllerServiceImpl implements ControllerService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public ControllerServiceImpl(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public User convertUser(RegistrationRequestDto requestDto){
        return userService.findByLogin(requestDto.getEmail());
    }
    public User convertUser(AuthenticationRequestDto requestDto){
        return userService.findByLogin(requestDto.getUsername());
    }

    @Override
    public Map<Object, Object> generateTokens(User user) {

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(),user.getPassword(),user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(),user.getId());

        Map<Object,Object> response = new HashMap<>();
        response.put("accessToken",accessToken);
        response.put("refreshToken",refreshToken);

        return response;
    }
}
