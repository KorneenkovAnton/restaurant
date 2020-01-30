package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.OrderDto;
import com.restaurant.restaurant.dto.OrderUpdateDto;
import com.restaurant.restaurant.dto.UserDto;
import com.restaurant.restaurant.entity.Dish;
import com.restaurant.restaurant.entity.DishType;
import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.dish.DishService;
import com.restaurant.restaurant.service.order.OrderService;
import com.restaurant.restaurant.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("resto/V1/")
public class OrderRestControllerV1 {

    private final OrderService orderService;
    private final DishService dishService;
    private final UserService userService;

    @Autowired
    public OrderRestControllerV1(OrderService orderService, DishService dishService, UserService userService) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.userService = userService;
    }

    @GetMapping("/dish/getByType/{type}")
    public ResponseEntity getDishByType(@PathVariable String type){
        List<Dish> dishes = dishService.findByType(type);

        if(!dishes.isEmpty()){
            return ResponseEntity.ok(dishes);
        }else {
            return ResponseEntity.badRequest().body("No dishes found");
        }
    }

    @GetMapping("/dishType/getAll/")
    public ResponseEntity getTypes(){
        List<DishType> dishTypes = dishService.getAllTypes();

        if(!dishTypes.isEmpty()){
            return ResponseEntity.ok(dishTypes);
        }else {
            return ResponseEntity.badRequest().body("No types found");
        }
    }

    @GetMapping("/order/get/{id:[0-9]}")
    public ResponseEntity getOrderById(@PathVariable Long id){
        Order order = orderService.findById(id);

        if(order != null){
            return ResponseEntity.ok(order);
        }else {
            return ResponseEntity.badRequest().body("No order found");
        }
    }

    @GetMapping("/order/getByUser")
    public ResponseEntity getByUser(Authentication authentication){
        List<Order> orders = orderService.getUserOrders(userService.findByLogin(authentication.getName()));

        if(!orders.isEmpty()){
            return ResponseEntity.ok(orders);
        }else {
            return ResponseEntity.badRequest().body("No orders found");
        }
    }

    @PostMapping("/order/save")
    public ResponseEntity save(@RequestBody OrderDto orderDto, Authentication authentication){
        Order order = new Order(orderDto);
        order.setUser(userService.findByLogin(authentication.getName()));
        if(orderService.save(order) != null){
            return ResponseEntity.ok("Ok");
        }else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/order/update")
    public ResponseEntity update(@RequestBody OrderUpdateDto orderDto){
        Order order = new Order(orderDto);
        order.setId(orderDto.getId());

        if(orderService.update(order) != null){
            return ResponseEntity.ok("Ok");
        }else {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
