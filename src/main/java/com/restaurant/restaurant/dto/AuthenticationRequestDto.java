package com.restaurant.restaurant.dto;

import lombok.Data;

/**
 * Created by Антон on 09.01.2020.
 */
@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
