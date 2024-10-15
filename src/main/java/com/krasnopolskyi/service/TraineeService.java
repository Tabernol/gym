package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.List;
import java.util.Optional;

public interface TraineeService extends BaseCrudService<TraineeResponseDto, TraineeDto> {
    TraineeResponseDto findByUsername(String username) throws EntityException;
    boolean delete(String username) throws EntityException;
    List<TrainerResponseDto> updateTrainers(List<TrainerDto> trainerDtoList);
}
