package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.repository.TrainerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class TrainerRepositoryImpl implements TrainerRepository {


    public Trainer save(Trainer trainer) {
        return trainer;
    }

    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable( null);
    }
}
