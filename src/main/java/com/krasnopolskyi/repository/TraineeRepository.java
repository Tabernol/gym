package com.krasnopolskyi.repository;

import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository extends BaseCrudRepository<Trainee> {
    Optional<Trainee> findByUsername(String username);
    boolean delete(String username);
    List<Trainer> updateTrainers(List<Trainer> trainerList);
}
