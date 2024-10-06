package com.krasnopolskyi.service;


import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;

public interface UserService {
    User save(UserDto user);

    User findById(Long id) throws EntityNotFoundException;

    User update(User user);

    boolean delete(User user);
}
