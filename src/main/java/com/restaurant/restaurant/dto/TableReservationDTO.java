package com.restaurant.restaurant.dto;

import com.restaurant.restaurant.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableReservationDTO {

    private long reservationDate;
    private com.restaurant.restaurant.entity.Table tables;
    private User user;
}
