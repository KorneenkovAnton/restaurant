package com.restaurant.restaurant.service.order;

import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.repository.OrderRepository;
import com.restaurant.restaurant.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        return order.orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepository.getByUserIs(user);
    }
}
