package com.krasnopolskyi.service;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityNotFoundException;

public interface TrainingTypeService {
    TrainingType findById(Integer id) throws EntityNotFoundException;

}
