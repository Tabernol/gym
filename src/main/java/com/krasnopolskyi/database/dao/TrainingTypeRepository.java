package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.TrainingType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingTypeRepository {

    public Optional<TrainingType> findById(Integer id) {
        return Optional.ofNullable(null);
    }
}
