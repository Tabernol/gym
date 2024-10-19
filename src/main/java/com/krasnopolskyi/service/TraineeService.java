package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.exception.EntityException;

import java.util.List;

public interface TraineeService extends BaseCrudService<TraineeResponseDto, TraineeDto> {
    TraineeResponseDto findByUsername(String username) throws EntityException;

    boolean delete(String username) throws EntityException;

    List<TrainerResponseDto> updateTrainers(TraineeDto traineeDto, List<TrainerDto> trainerDtoList)
            throws EntityException;

    List<TrainerResponseDto> findAllNotAssignedTrainersByTrainee(String username) throws EntityException;
}
