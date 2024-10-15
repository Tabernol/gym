package com.krasnopolskyi.repository;

import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends BaseCrudRepository<Trainer>{
    Optional<Trainer> findByUsername(String username);


}
