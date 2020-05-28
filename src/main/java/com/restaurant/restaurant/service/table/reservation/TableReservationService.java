package com.restaurant.restaurant.service.table.reservation;

import com.restaurant.restaurant.entity.TableReservation;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.Service;

public interface TableReservationService extends Service<TableReservation> {
    TableReservation findByUser(User user);
}
