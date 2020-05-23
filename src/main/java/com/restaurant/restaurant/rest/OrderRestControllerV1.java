package com.restaurant.restaurant.rest;

import com.restaurant.restaurant.dto.OrderDto;
import com.restaurant.restaurant.dto.OrderUpdateDto;
import com.restaurant.restaurant.entity.Dish;
import com.restaurant.restaurant.entity.DishType;
import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.Table;
import com.restaurant.restaurant.service.dish.DishService;
import com.restaurant.restaurant.service.order.OrderService;
import com.restaurant.restaurant.service.table.TableService;
import com.restaurant.restaurant.service.user.UserService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("resto/V1/")
@CrossOrigin
public class OrderRestControllerV1 {

    private final OrderService orderService;
    private final DishService dishService;
    private final UserService userService;
    private final TableService tableService;
    private final SimpMessagingTemplate template;

    @Autowired
    public OrderRestControllerV1(OrderService orderService, DishService dishService, UserService userService,
                                 TableService tableService, SimpMessagingTemplate template) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.userService = userService;
        this.tableService = tableService;
        this.template = template;
    }

    @GetMapping("/dish/getByType/{type}")
    public ResponseEntity getDishByType(@PathVariable String type) {
        List<Dish> dishes = dishService.findByType(type);

        if (!dishes.isEmpty()) {
            return ResponseEntity.ok(dishes);
        } else {
            return ResponseEntity.badRequest().body("No dishes found");
        }

    }

    @GetMapping("/dishType/getAll/")
    public ResponseEntity getTypes() {
        List<DishType> dishTypes = dishService.getAllTypes();

        if (!dishTypes.isEmpty()) {
            return ResponseEntity.ok(dishTypes);
        } else {
            return ResponseEntity.badRequest().body("No types found");
        }
    }

    @PostMapping("/dishType/save")
    public ResponseEntity saveDishType(@RequestBody DishType type) {
        DishType dishType = dishService.saveDishType(type);

        if (dishType != null) {
            return ResponseEntity.ok(dishType);
        } else {
            return ResponseEntity.badRequest().body("Error save type");
        }
    }

    @GetMapping("/order/get/{id}")
    public ResponseEntity getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("No order found");
        }
    }

    @GetMapping("/order/getByUser")
    public ResponseEntity getByUser(Authentication authentication) {
        List<Order> orders = orderService.getUserOrders(userService.findByLogin(authentication.getName()));

        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.badRequest().body("No orders found");
        }
    }

    @PostMapping("/order/save")
    public ResponseEntity save(@RequestBody OrderDto orderDto, Authentication authentication) {
        Order order = new Order(orderDto);
        order.setUser(userService.findByLogin(authentication.getName()));
        if (!orderDto.getDishes().isEmpty() && orderService.save(order) != null) {
            template.convertAndSend("/orders/notification", order);
            return ResponseEntity.ok("Ok");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/order/update")
    public ResponseEntity update(@RequestBody OrderUpdateDto orderDto) {
        Order order = new Order(orderDto);
        order.setId(orderDto.getId());
        return ResponseEntity.ok(orderService.update(order));
    }

    @GetMapping("/table/getAll")
    public ResponseEntity getAllTable() {
        List<Table> tables = tableService.getAll();

        if (!tables.isEmpty()) {
            return ResponseEntity.ok(tables);
        } else {
            return ResponseEntity.badRequest().body("No available tables");
        }
    }

    @GetMapping("/order/getAll")
    public ResponseEntity getAllOrders() {
        List<Order> orders = orderService.getAllPreparingOrders();

        if (orders.isEmpty()) {
            return ResponseEntity.badRequest().body("No available orders");
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PostMapping("/table/update")
    public ResponseEntity updateTable(@RequestBody Table table) {
        Table updatedTable = tableService.update(table);

        if (updatedTable != null) {
            return ResponseEntity.ok(table);
        } else {
            return ResponseEntity.badRequest().body("Wrong table");
        }
    }

    @PostMapping("/dish/save")
    public ResponseEntity save(@RequestBody Dish dish) {
        Dish newDish = dishService.save(dish);

        return newDish != null ? ResponseEntity.ok(newDish) : ResponseEntity.badRequest().body("Error save dish");
    }
}
