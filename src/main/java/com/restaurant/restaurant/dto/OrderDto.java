package com.restaurant.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.restaurant.entity.OrderDetails;
import com.restaurant.restaurant.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Антон on 19.01.2020.
 */
@Data
public class OrderDto {
    @JsonIgnore
    private Date date = null;

    private Integer amount;
    private String status;
    private String info;
    private User user;
    private com.restaurant.restaurant.entity.Table table;
    private List<OrderDetailsDto> dishes;
    private String receiptPath;
}
