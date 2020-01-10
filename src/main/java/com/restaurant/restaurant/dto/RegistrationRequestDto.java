package com.restaurant.restaurant.dto;

import lombok.Data;

/**
 * Created by Антон on 09.01.2020.
 */
@Data
public class RegistrationRequestDto {
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String address;
    private String phonenumber;
    private String role;
}
