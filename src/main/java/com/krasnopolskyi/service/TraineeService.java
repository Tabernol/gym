package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.exception.EntityNotFoundException;

public interface TraineeService {
    UserCredentials save(TraineeDto trainee);

    Trainee findById(Long id) throws EntityNotFoundException;

    Trainee update(Trainee trainee);

    boolean delete(Trainee trainee);
}
