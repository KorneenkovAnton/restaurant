package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.AuthenticationRequestDto;
import com.restaurant.restaurant.dto.RefreshJwtRequestDto;
import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.security.jwt.JwtAuthenticationException;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import com.restaurant.restaurant.service.UserService;
import com.restaurant.restaurant.validator.UserValidator;
import com.restaurant.restaurant.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Антон on 09.01.2020.
 */
@RestController
@RequestMapping(value = "/resto/V1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,requestDto.getPassword()));
            User user = userService.findByLogin(username);

            if( user == null){
                throw new UsernameNotFoundException("email");
            }

            String accessToken = jwtTokenProvider.createAccessToken(username,user.getPassword(),user.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(username,user.getId());

            Map<Object,Object> response = new HashMap<>();
            response.put("accessToken",accessToken);
            response.put("refreshToken",refreshToken);

            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid email or pass");
        }
    }

    @PostMapping("refresh")
    public ResponseEntity refreshToken(@RequestBody RefreshJwtRequestDto requestDto) throws JwtAuthenticationException {
        Map<Object, Object> response = null;
        String username;

        if(jwtTokenProvider.validateToken(requestDto.getRefreshToken())){
            username = jwtTokenProvider.getUsername(requestDto.getRefreshToken());
            User user = userService.findByLogin(username);

            if(user == null){
                throw new UsernameNotFoundException(username);
            }

            String accessToken = jwtTokenProvider.createAccessToken(username,user.getPassword(),user.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(username,user.getId());

            response = new HashMap<>();
            response.put("accessToken",accessToken);
            response.put("refreshToken",refreshToken);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto registrationRequestDto){
        Map<Object, Object> response  = new HashMap<>();
        Validator validator = new UserValidator();

        if(validator.validate(registrationRequestDto)){

            User user = new User(registrationRequestDto);
            user = userService.register(user);

            String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(),user.getPassword(),user.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(),user.getId());

            response.put("accessToken",accessToken);
            response.put("refreshToken",refreshToken);

            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body("ValidationError");
        }
    }
}
