package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.UserDto;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import com.restaurant.restaurant.service.UserService;
import com.restaurant.restaurant.validator.UserValidator;
import com.restaurant.restaurant.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/resto/V1/user/")
public class UserRestControllerV1  {

    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserRestControllerV1(UserService userService,JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping("/get/{id:[0-9]}")
    public ResponseEntity getUserById(@PathVariable Long id){
        Map<Object,Object> response = new HashMap<>();
        User user = userService.findById(id);

        if(user != null) {
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("lastname", user.getLastname());
            response.put("address", user.getAddress());
            response.put("phonenumber", user.getPhonenumber());

            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.badRequest().body("No user Found");
        }
    }

    @GetMapping("/delete/{id:[0-9]}")
    public ResponseEntity deleteUserById(@PathVariable Long id){
        userService.delete(id);

        return ResponseEntity.ok("User deleted");
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDto requestDto, HttpServletRequest request){
        Validator validator = new UserValidator();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (validator.validate(requestDto) &&
                jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request,"Authorization"))
                        .equals(requestDto.getEmail())&&
                jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request,"Authorization"))
                        .compareTo(requestDto.getId())==0) {

            User user = new User(requestDto);
            user.setId(requestDto.getId());
            user.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));

            userService.update(user);

            return ResponseEntity.ok("user "+user.getEmail()+" updated");
        }else {
            return ResponseEntity.unprocessableEntity().body("Validation Error");
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }
}
