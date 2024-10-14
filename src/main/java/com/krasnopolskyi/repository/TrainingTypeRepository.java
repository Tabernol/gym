package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.Optional;

public interface TrainingTypeRepository {
    Optional<TrainingType> findById(Integer id);
}
