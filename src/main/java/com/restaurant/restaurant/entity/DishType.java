package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Антон on 09.01.2020.
 */
@Entity
@Table(name = "dish_type")
@Data
public class DishType extends BaseEntity {
    private String name;

    public DishType(String name) {
        this.name = name;
    }

    public DishType() {
    }
}
