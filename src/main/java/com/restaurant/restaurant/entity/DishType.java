package com.restaurant.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Антон on 09.01.2020.
 */
@Entity
@Table(name = "dish_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishType extends BaseEntity {
    private String name;
    @Column(name = "image_path")
    private String imagePath;
}
