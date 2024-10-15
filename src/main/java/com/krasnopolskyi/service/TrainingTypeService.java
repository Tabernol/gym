package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.List;

public interface TrainingTypeService {
    TrainingType findById(Integer id) throws EntityException;

}
