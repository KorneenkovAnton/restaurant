package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Антон on 09.01.2020.
 */
@Entity
@Table(name = "stuff")
@Data
public class Staff extends BaseEntity {
    private String status;
    private String name;
    private String lastname;
    private String profession;
    private float rating;
}
