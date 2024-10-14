package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.Trainee;

import java.util.Optional;

public interface TraineeRepository extends BaseCrudRepository<Trainee> {
    Optional<Trainee> findByUsername(String username);
    boolean delete(String username);
}
