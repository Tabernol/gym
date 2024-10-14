package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.Optional;

public interface TraineeService extends BaseCrudService<TraineeResponseDto, TraineeDto> {
    TraineeResponseDto findByUsername(String username) throws EntityException;
    boolean delete(String username) throws EntityException;
}
