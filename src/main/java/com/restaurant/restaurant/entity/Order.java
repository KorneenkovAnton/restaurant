package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "order")
@Data
public class Order extends BaseEntity {
    private Date date;
    private Integer amount;
    private String status;
    private String info;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private com.restaurant.restaurant.entity.Table table;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderDetails> dishes;
}
