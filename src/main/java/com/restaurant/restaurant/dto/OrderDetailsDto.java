package com.restaurant.restaurant.dto;

import lombok.Data;

/**
 * Created by Антон on 30.01.2020.
 */
@Data
public class OrderDetailsDto {
    private Integer num;
    private DishDto dish;
}
