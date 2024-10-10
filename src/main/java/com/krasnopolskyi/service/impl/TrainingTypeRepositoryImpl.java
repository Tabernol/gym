package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.entity.TrainingType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingTypeRepositoryImpl {

    public Optional<TrainingType> findById(Integer id) {
        return Optional.ofNullable(null);
    }
}
