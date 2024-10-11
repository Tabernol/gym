package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.GymException;

public interface TrainerService {

    TrainerResponseDto save(TrainerDto trainerDto) throws GymException;

    TrainerResponseDto findById(Long id) throws EntityNotFoundException;

    TrainerResponseDto update(TrainerDto trainerDto) throws GymException;
}
