package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.repository.TrainerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    @Override
    public Trainer save(Trainer trainer) {
        return null;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return Optional.empty();
    }
}
