package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "dish")
@Data
public class Dish extends BaseEntity {
    private String name;
    private String description;
    private Integer cost;
    private Boolean availability;

    @ManyToOne
    @JoinColumn(name = "dish_type_id")
    private DishType type;

}
