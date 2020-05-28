package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.TableReservation;
import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {
    TableReservation findByUser(User user);
}
