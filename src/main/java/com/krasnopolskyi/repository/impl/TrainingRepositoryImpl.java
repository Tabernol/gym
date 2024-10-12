package com.krasnopolskyi.repository.impl;


import com.krasnopolskyi.entity.Training;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingRepositoryImpl {

    public Training save(Training training) {
        return training;
    }

    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(null);
    }

}
