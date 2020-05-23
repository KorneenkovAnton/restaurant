package com.restaurant.restaurant.service.order;

import com.itextpdf.text.DocumentException;
import com.restaurant.restaurant.entity.Order;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.repository.OrderRepository;
import com.restaurant.restaurant.service.table.TableService;
import com.restaurant.restaurant.util.ReceiptPrinter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TableService tableService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, TableService tableService) {
        this.orderRepository = orderRepository;
        this.tableService = tableService;
    }

    @Override
    public Order save(Order order) {
//        tableService.updateTable(order.getTable().getStatus(),order.getUser(),order.getTable().getName());
        order.setTable(tableService.findByName(order.getTable().getName()));
        order.getTable().setUser(order.getUser());
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order update(Order order) {
        String receiptPath;
        Order updateOrder = orderRepository.getById(order.getId());
        updateOrder.setStatus(order.getStatus());
        if(order.getStatus().equals("Done")){
            try {
                System.out.println("printing");
                receiptPath = new ReceiptPrinter().printReceipt(updateOrder);
                updateOrder.setReceiptPath(receiptPath);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
            }
        }
        orderRepository.saveAndFlush(updateOrder);
        return updateOrder;
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

    @Override
    public List<Order> getAllPreparingOrders() {
        return orderRepository.findAllByStatus("Preparing");
    }
}
