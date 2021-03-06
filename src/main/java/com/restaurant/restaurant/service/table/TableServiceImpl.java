package com.restaurant.restaurant.service.table;

import com.restaurant.restaurant.entity.Table;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Антон on 31.01.2020.
 */
@Service
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public Table save(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public Table update(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public Table findById(Long id) {
        return null;
    }

    @Override
    public List<Table> getAll() {
        return tableRepository.findAll();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public String updateTable(String status, User user, String name) {
        return String.valueOf(tableRepository.updateTableStatusAndUserIdByTableName(status,user,name));
    }

    @Override
    public Table findByName(String name) {
        return tableRepository.findByName(name);
    }
}
