package com.krasnopolskyi.service;

import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;

public interface TrainerService {

    UserCredentials save(TrainerDto trainerDto) throws ValidateException;

    Trainer findById(Long id) throws EntityException;

    Trainer update(Trainer Trainer);
}
