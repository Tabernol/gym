package com.krasnopolskyi.database.dao;


import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.database.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TraineeRepository {

    private Storage storage;

    // initialized via setter because task condition 4
    // I prefer initialized via constructor
    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Trainee save(Trainee trainee) {
        // need to save into map twice because when do it at first time Map returns null
        storage.getTrainees().put(trainee.getId(), trainee);
        return storage.getTrainees().put(trainee.getId(), trainee);
    }

    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(storage.getTrainees().getOrDefault(id, null));
    }

    public boolean delete(Trainee trainee) {
        return storage.getTrainees().remove(trainee.getId(), trainee);
    }
}
