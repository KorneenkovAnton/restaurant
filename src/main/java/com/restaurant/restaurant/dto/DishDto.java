package com.restaurant.restaurant.dto;

import lombok.Data;

/**
 * Created by Антон on 30.01.2020.
 */
@Data
public class DishDto {
    private Long id;
    private String name;
    private String description;
    private Integer cost;
    private Boolean availability;
}
