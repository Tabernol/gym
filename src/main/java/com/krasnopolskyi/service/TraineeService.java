package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

public interface TraineeService extends BaseCrudService<TraineeResponseDto, TraineeDto> {
    TraineeResponseDto save(TraineeDto traineeDto) throws ValidateException;

    TraineeResponseDto findById(Long id) throws EntityException;

    TraineeResponseDto findByUsername(String username) throws EntityException;

    TraineeResponseDto update(TraineeDto traineeDto) throws EntityException;

    boolean delete(String username) throws EntityException;
}
