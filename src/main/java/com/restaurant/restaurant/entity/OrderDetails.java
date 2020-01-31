package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetails extends BaseEntity {
    private int num;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id",insertable = false,updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public OrderDetails(int num, Order order, Dish dish) {
        this.num = num;
        this.order = order;
        this.dish = dish;
    }

    public OrderDetails() {
    }
}
