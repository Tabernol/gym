package com.krasnopolskyi.service;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;


public interface TrainingTypeService {
    TrainingType findById(Integer id) throws EntityException;

}
