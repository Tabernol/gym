package com.krasnopolskyi.service;


import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;

import java.util.Optional;

public interface UserService {
    User create(UserDto user) throws ValidateException;
    User findById(Long id) throws EntityException;
    User findByUsername(String username) throws EntityException;
    boolean checkCredentials(UserCredentials credentials) throws EntityException;
    User changePassword(String username, String password) throws GymException;
    User changeActivityStatus(String target) throws GymException;
}
