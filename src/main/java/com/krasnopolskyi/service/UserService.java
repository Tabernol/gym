package com.krasnopolskyi.service;


import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;

public interface UserService {
    User create(UserDto user);

    User findById(Long id) throws EntityException;

    User update(User user);

    boolean delete(User user);
}
