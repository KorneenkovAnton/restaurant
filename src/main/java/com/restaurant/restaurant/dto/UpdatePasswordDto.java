package com.restaurant.restaurant.dto;

import lombok.Data;

/**
 * Created by Антон on 30.01.2020.
 */
@Data
public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
}
