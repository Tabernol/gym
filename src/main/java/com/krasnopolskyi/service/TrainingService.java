package com.krasnopolskyi.service;

import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityNotFoundException;

public interface TrainingService {
    Training save(Training training);

    Training findById(Long id) throws EntityNotFoundException;
}
