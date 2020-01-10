package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.AuthenticationRequestDto;
import com.restaurant.restaurant.dto.RefreshJwtRequestDto;
import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.security.jwt.JwtAuthenticationException;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import com.restaurant.restaurant.service.controller.ControllerService;
import com.restaurant.restaurant.service.controller.ControllerServiceImpl;
import com.restaurant.restaurant.service.user.UserService;
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

@RestController
@RequestMapping(value = "/resto/V1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final ControllerService controllerServiceImpl;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager,
                                          JwtTokenProvider jwtTokenProvider,
                                          UserService userService,
                                          ControllerServiceImpl authenticationRestControllerServiceImpl) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.controllerServiceImpl = authenticationRestControllerServiceImpl;
    }



    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,requestDto.getPassword()));
            User user = controllerServiceImpl.convertUser(requestDto);

            if( user == null){
                throw new UsernameNotFoundException("email");
            }

            return ResponseEntity.ok(controllerServiceImpl.generateTokens(user));
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid email or pass");
        }
    }



    @PostMapping("refresh")
    public ResponseEntity refreshToken(@RequestBody RefreshJwtRequestDto requestDto) throws JwtAuthenticationException {
        String username;
        User user = null;

        if(jwtTokenProvider.validateToken(requestDto.getRefreshToken())){

            username = jwtTokenProvider.getUsername(requestDto.getRefreshToken());
            user = userService.findByLogin(username);

            if(user == null){
                throw new UsernameNotFoundException(username);
            }
        }
        return ResponseEntity.ok(controllerServiceImpl.generateTokens(user));
    }



    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto registrationRequestDto){
        Validator validator = new UserValidator();

        if(validator.validate(registrationRequestDto)){

            User user = new User(registrationRequestDto);
            user = userService.register(user);

            return ResponseEntity.ok(controllerServiceImpl.generateTokens(user));
        }else {
            return ResponseEntity.badRequest().body("ValidationError");
        }
    }
}
