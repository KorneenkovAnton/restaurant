package com.restaurant.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "table_reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableReservation extends BaseEntity {
    @Column(name = "reservation_date")
    private Date reservationDate;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private com.restaurant.restaurant.entity.Table tables;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
