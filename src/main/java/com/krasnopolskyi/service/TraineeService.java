package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;

public interface TraineeService {
    TraineeResponseDto save(TraineeDto traineeDto) throws ValidateException;

    TraineeResponseDto findById(Long id) throws EntityNotFoundException;

    TraineeResponseDto update(TraineeDto traineeDto) throws EntityNotFoundException;

    boolean delete(TraineeDto traineeDto) throws EntityNotFoundException;
}
