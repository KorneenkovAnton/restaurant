package com.restaurant.restaurant.service.table;

import com.restaurant.restaurant.entity.Table;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.service.Service;

/**
 * Created by Антон on 31.01.2020.
 */
public interface TableService extends Service<Table> {
    String updateTable(String status, User user, String name);
    Table findByName(String name);
}
