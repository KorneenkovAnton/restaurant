package com.restaurant.restaurant.service.table.reservation;

import com.restaurant.restaurant.entity.TableReservation;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.repository.TableReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableReservationServiceImpl implements TableReservationService {

    private final TableReservationRepository tableReservationRepository;

    @Autowired
    public TableReservationServiceImpl(TableReservationRepository tableReservationRepository) {
        this.tableReservationRepository = tableReservationRepository;
    }

    @Override
    public TableReservation save(TableReservation tableReservation) {
        return tableReservationRepository.save(tableReservation);
    }

    @Override
    public TableReservation update(TableReservation tableReservation) {
        return null;
    }

    @Override
    public TableReservation findById(Long id) {

        return tableReservationRepository.findById(id).get();
    }

    @Override
    public List<TableReservation> getAll() {
        return tableReservationRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        tableReservationRepository.delete(findById(id));
    }

    @Override
    public TableReservation findByUser(User user) {
        return tableReservationRepository.findByUser(user);
    }
}
