package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.List;

public interface TrainingService {
    TrainingResponseDto save(TrainingDto trainingDto) throws ValidateException, EntityException;

    TrainingResponseDto findById(Long id) throws EntityException;

    List<TrainingResponseDto> getFilteredTrainings(TrainingFilterDto filter) throws EntityException;
}
