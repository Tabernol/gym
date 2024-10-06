package com.krasnopolskyi.database.dao;


import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.database.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingRepository {

    private Storage storage;

    // initialized via setter because task condition 4
    // I prefer initialized via constructor
    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Training save(Training training) {
        // need to save into map twice because when do it at first time Map returns null
        storage.getTrainings().put(training.getId(), training);
        return storage.getTrainings().put(training.getId(), training);
    }

    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(storage.getTrainings().getOrDefault(id, null));
    }
}
