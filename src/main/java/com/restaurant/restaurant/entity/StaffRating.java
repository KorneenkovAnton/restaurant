package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Антон on 09.01.2020.
 */
@Entity
@Table(name = "staff_rating")
@Data
public class StaffRating extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private float rating;
}
