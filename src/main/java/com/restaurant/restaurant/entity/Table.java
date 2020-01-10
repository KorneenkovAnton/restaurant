package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.Table(name = "table")
@Data
public class Table extends BaseEntity {
    private String name;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
