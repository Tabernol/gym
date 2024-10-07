package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.Optional;

public interface TrainerService {

    UserCredentials save(TrainerDto trainerDto) throws ValidateException;

    Trainer findById(Long id) throws EntityNotFoundException;

    Trainer update(Trainer Trainer);
}
