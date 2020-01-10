package com.restaurant.restaurant.service.controller;

import com.restaurant.restaurant.dto.AuthenticationRequestDto;
import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.dto.UserDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import com.restaurant.restaurant.service.user.UserService;
import com.restaurant.restaurant.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Антон on 10.01.2020.
 */
@Service
public class ControllerServiceImpl implements ControllerService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final Validator validator;

    @Autowired
    public ControllerServiceImpl(JwtTokenProvider jwtTokenProvider, UserService userService, Validator validator) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public boolean getUpdateAccess(UserDto requestDto,HttpServletRequest request) {
        return validator.validate(requestDto) &&
                jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request,"Authorization"))
                        .equals(requestDto.getEmail())&&
                jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request,"Authorization"))
                        .compareTo(requestDto.getId())== 0;
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
