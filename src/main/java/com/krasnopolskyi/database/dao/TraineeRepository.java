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
        storage.getTrainees().put(trainee.getId(), trainee);
        return storage.getTrainees().get(trainee.getId()); // saving to a file is done implicitly using AOP
    }

    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(storage.getTrainees().getOrDefault(id, null));
    }

    public boolean delete(Trainee trainee) {
        return storage.getTrainees().remove(trainee.getId(), trainee);
    } // deleting to a file is done implicitly using AOP
}
