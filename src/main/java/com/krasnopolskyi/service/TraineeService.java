package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.exception.EntityException;

public interface TraineeService {
    TraineeResponseDto save(TraineeDto trainee) throws EntityException;

    Trainee findById(Long id) throws EntityException;

    Trainee update(Trainee trainee);

    boolean delete(Trainee trainee);
}
