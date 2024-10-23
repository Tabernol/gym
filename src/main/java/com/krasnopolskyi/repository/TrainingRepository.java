package com.krasnopolskyi.repository;

import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.entity.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository {
    Training save(Training training);

    Optional<Training> findById(Long id);

    List<Training> getFilteredTrainings(TrainingFilterDto filter);
}
