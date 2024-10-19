package com.krasnopolskyi.service;


import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;

public interface UserService {
    User create(UserDto user);
    User findById(Long id) throws EntityException;
    boolean checkCredentials(UserCredentials credentials) throws EntityException;
    User changePassword(String username, String password) throws GymException;
    User changeActivityStatus(String target) throws GymException;
}
