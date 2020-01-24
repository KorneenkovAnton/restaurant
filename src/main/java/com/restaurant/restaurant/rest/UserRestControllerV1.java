package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.RegistrationRequestDto;
import com.restaurant.restaurant.dto.UserDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.controller.ControllerService;
import com.restaurant.restaurant.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/resto/V1/user/")
public class UserRestControllerV1  {

    private final UserService userService;
    private final ControllerService controllerService;

    @Autowired
    public UserRestControllerV1(UserService userService,
                                ControllerService controllerService) {

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
    public ResponseEntity updateUser(@RequestBody RegistrationRequestDto requestDto, Authentication authentication){
        Map<Object, Object> response;

        if (requestDto.getEmail().equals(authentication.getName())) {

            User user = controllerService.convertUser(requestDto);

            response = controllerService.generateTokens(userService.update(user));

            response.put("user",user);

            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.unprocessableEntity().body("Validation Error");
        }
    }

    @GetMapping("/getForUpdate")
    public ResponseEntity getUserForUpdate(Authentication authentication){
        return ResponseEntity.ok(userService.findByLogin(authentication.getName()));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

}
