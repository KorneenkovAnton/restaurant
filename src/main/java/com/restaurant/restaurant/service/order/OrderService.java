package com.restaurant.restaurant.service.order;

import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.Service;

import java.util.List;

/**
 * Created by Антон on 19.01.2020.
 */
public interface OrderService extends Service<Order> {

    List<Order> getUserOrders(User user);
    List<Order> getAllPreparingOrders();

}
