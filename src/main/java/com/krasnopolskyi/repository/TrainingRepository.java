package com.krasnopolskyi.repository;

import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.entity.Training;

import java.util.List;

public interface TrainingRepository {

    Training save(TrainingDto trainingDto);

    List<Training> findAllByUsername(String username);
}
