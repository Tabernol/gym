package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.database.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class TrainerRepository {
    private Storage storage;

    // initialized via setter because task condition 4
    // I prefer initialized via constructor
    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Trainer save(Trainer trainer) {
        // need to save into map twice because when do it at first time Map returns null
        storage.getTrainers().put(trainer.getId(), trainer);
        return storage.getTrainers().put(trainer.getId(), trainer); // saving to a file is done implicitly using AOP
    }

    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(storage.getTrainers().getOrDefault(id, null));
    }
}
