package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "dish")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish extends BaseEntity {
    private String name;
    private String description;
    private Integer cost;
    private Boolean availability;
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "dish_type_id")
    private DishType type;


    public Dish(Long id,String name, String description, Integer cost, Boolean availability, DishType type) {
        super(id);
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.availability = availability;
        this.type = type;
    }
}
