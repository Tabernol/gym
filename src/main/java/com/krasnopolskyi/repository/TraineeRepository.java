package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.Trainee;

import java.util.Optional;

public interface TraineeRepository {
    Optional<Trainee> save(Trainee trainee);
    Optional<Trainee> findById(Long id);
}
