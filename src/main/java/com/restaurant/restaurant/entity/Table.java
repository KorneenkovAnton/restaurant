package com.restaurant.restaurant.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@javax.persistence.Table(name = "rest_table")
@Data
public class Table extends BaseEntity {
    private String name;
    private String status;
    @Column(name = "reservation_date")
    private Date reservationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
