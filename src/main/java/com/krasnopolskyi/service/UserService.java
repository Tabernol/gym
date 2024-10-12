package com.krasnopolskyi.service;


import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;

public interface UserService {
    User save(UserDto user) throws ValidateException;

    User findById(Long id) throws EntityException;

    User update(User user);

    boolean delete(User user);
}
