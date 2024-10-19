package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends BaseCrudRepository<Trainer>{
    Optional<Trainer> findByUsername(String username);
    List<Trainer> findAll();


}
