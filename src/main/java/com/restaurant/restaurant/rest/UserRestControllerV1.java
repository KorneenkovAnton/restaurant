package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.UserDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import com.restaurant.restaurant.service.controller.ControllerService;
import com.restaurant.restaurant.service.user.UserService;
import com.restaurant.restaurant.validator.UserValidator;
import com.restaurant.restaurant.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/resto/V1/user/")
public class UserRestControllerV1  {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ControllerService controllerService;

    @Autowired
    public UserRestControllerV1(UserService userService,
                                JwtTokenProvider jwtTokenProvider,
                                ControllerService controllerService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.controllerService = controllerService;
    }


    @GetMapping("/get/{id:[0-9]}")
    public ResponseEntity getUserById(@PathVariable Long id){
        User user = userService.findById(id);

        if(user != null) {

            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.badRequest().body("No user Found");
        }
    }


    @DeleteMapping("/delete/{id:[0-9]}")
    public ResponseEntity deleteUserById(@PathVariable Long id){
        userService.delete(id);

        return ResponseEntity.ok("User deleted");
    }


    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDto requestDto, HttpServletRequest request){
        Validator validator = new UserValidator();
        Map<Object, Object> response;

        if (validator.validate(requestDto) &&
                jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request,"Authorization"))
                        .equals(requestDto.getEmail())&&
                jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request,"Authorization"))
                        .compareTo(requestDto.getId())== 0) {

            User user = new User(requestDto);
            user.setId(requestDto.getId());

            response = controllerService.generateTokens(user = userService.update(user));

            response.put("answer", "user " + user.getEmail() + " updated");

            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.unprocessableEntity().body("Validation Error");
        }
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

}
