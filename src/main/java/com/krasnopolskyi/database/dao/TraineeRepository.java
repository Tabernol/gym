package com.krasnopolskyi.database.dao;


import com.krasnopolskyi.entity.Trainee;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TraineeRepository {

    public Trainee save(Trainee trainee) {
        return trainee;
    }

    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable( null);
    }

    public boolean delete(Trainee trainee) {
        return false;
    }
}
