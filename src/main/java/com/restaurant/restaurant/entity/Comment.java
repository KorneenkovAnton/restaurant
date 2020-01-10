package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
public class Comment extends BaseEntity {
    private String text;
    private Date date;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
}
