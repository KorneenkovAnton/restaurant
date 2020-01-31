package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurant.restaurant.dto.OrderDetailsDto;
import com.restaurant.restaurant.dto.OrderDto;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "rest_order")
@Data
public class Order extends BaseEntity {
    @Column(name = "order_date")
    private Date date;
    private Integer amount;
    @Column(name = "order_status")
    private String status;
    @Column(name = "order_info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private com.restaurant.restaurant.entity.Table table;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "order_id")
    private List<OrderDetails> dishes;

    public Order(OrderDto orderDto) {
        this.date = new java.sql.Date(new Date().getTime());
        this.amount = orderDto.getAmount();
        this.status = orderDto.getStatus();
        this.info = orderDto.getInfo();
        this.table = orderDto.getTable();
        this.dishes = new ArrayList<>();

        for (OrderDetailsDto or:orderDto.getDishes()
             ) {
            dishes.add(new OrderDetails(or.getNum(),this, new Dish(or.getDish().getId(),
                    or.getDish().getName(),or.getDish().getDescription(),
                    or.getDish().getCost(),true,null)));
        }
    }

    public Order() {
    }
}
