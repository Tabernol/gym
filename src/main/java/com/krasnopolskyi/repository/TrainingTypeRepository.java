package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;

import java.util.Optional;

public interface TrainingTypeRepository {
    Optional<TrainingType> findById(Integer id) throws EntityException;
}
