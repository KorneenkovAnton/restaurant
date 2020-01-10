package com.restaurant.restaurant.entity;

import com.restaurant.restaurant.dto.RegistrationRequestDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity {
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String address;
    private String phonenumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(RegistrationRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.lastname = requestDto.getLastname();
        this.address = requestDto.getAddress();
        this.phonenumber = requestDto.getPhonenumber();
        this.role = Role.valueOf(requestDto.getRole());
    }



    public User() {
    }
}
