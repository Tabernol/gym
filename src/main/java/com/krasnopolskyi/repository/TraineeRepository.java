package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.Trainee;


public interface TraineeRepository extends BaseCrudRepository<Trainee> {
    boolean delete(String username);
}
