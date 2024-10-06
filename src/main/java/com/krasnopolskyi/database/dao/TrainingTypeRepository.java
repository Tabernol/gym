package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingTypeRepository {
    @Autowired
    private Storage storage;

    public Optional<TrainingType> findById(Integer id) {
        return Optional.ofNullable(storage.getTrainingTypes().getOrDefault(id, null));
    }
}
