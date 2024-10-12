package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

public interface TraineeService {
    TraineeResponseDto save(TraineeDto traineeDto) throws ValidateException;

    TraineeResponseDto findById(Long id) throws EntityException;

    TraineeResponseDto update(TraineeDto traineeDto) throws EntityException;

    boolean delete(TraineeDto traineeDto) throws EntityException;
}
