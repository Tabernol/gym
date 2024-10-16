package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;

import java.util.List;

public interface TrainerService extends BaseCrudService<TrainerResponseDto, TrainerDto> {
    TrainerResponseDto findByUsername(String username) throws EntityException;
}
