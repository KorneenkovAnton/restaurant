package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Антон on 10.01.2020.
 */
@Entity
@Table(name = "order_details")
@Data
public class OrderDetails extends BaseEntity {
    private Long orderId;
    private int num;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;
}
