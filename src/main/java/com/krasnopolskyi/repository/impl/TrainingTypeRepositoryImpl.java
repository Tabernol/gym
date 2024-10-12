package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.repository.TrainingTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {
    @Override
    public Optional<TrainingType> findById(Integer id) throws EntityException {
        return Optional.empty();
    }
}
