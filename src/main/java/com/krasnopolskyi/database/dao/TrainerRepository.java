package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.Trainer;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class TrainerRepository {


    public Trainer save(Trainer trainer) {
        return trainer;
    }

    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable( null);
    }
}
