package com.restaurant.restaurant.rest;

import com.amazonaws.http.RepeatableInputStreamRequestEntity;
import com.restaurant.restaurant.dto.OrderDto;
import com.restaurant.restaurant.dto.OrderUpdateDto;
import com.restaurant.restaurant.dto.TableReservationDTO;
import com.restaurant.restaurant.entity.*;
import com.restaurant.restaurant.service.dish.DishService;
import com.restaurant.restaurant.service.order.OrderService;
import com.restaurant.restaurant.service.table.TableService;
import com.restaurant.restaurant.service.table.reservation.TableReservationService;
import com.restaurant.restaurant.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.SqlResultSetMapping;
import java.util.Date;
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
    private final TableReservationService tableReservationService;

    @Autowired
    public OrderRestControllerV1(OrderService orderService, DishService dishService, UserService userService,
                                 TableService tableService, SimpMessagingTemplate template, TableReservationService tableReservationService) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.userService = userService;
        this.tableService = tableService;
        this.template = template;
        this.tableReservationService = tableReservationService;
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

    @GetMapping("/reserve/getByUser")
    public ResponseEntity getReserveTableByUser(Authentication authentication) {
        TableReservation tableReservation = tableReservationService.findByUser(userService.findByLogin(authentication.getName()));
        return tableReservation != null ? ResponseEntity.ok(tableReservation) : ResponseEntity.ok("No reserved tables");
    }

    @PostMapping("/reserve/save")
    public ResponseEntity saveReservedTable(@RequestBody TableReservationDTO tableReservationDTO, Authentication authentication) {
        TableReservation tableReservation = new TableReservation();
        tableReservation.setReservationDate(new Date(tableReservationDTO.getReservationDate()));
        tableReservation.setTables(tableReservationDTO.getTables());
        tableReservation.setUser(userService.findByLogin(authentication.getName()));
        TableReservation savedReservation = tableReservationService.save(tableReservation);
        return savedReservation != null ? ResponseEntity.ok(savedReservation) :
                ResponseEntity.status(500).body("Error saving reserve");
    }

    @GetMapping("/reserve/all")
    public ResponseEntity allReservedTables(){
        return ResponseEntity.ok(tableReservationService.getAll());
    }

    @DeleteMapping("/reserve/delete/{id}")
    public ResponseEntity deleteReservedTable(@PathVariable Long id){
        tableReservationService.delete(id);
        return ResponseEntity.ok().body("Reserve delete " + id);
    }
}
