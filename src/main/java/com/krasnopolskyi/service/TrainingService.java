package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

public interface TrainingService {
    Training save(TrainingDto trainingDto) throws ValidateException;

    Training findById(Long id) throws EntityException;
}
