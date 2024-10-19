package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.GymException;

import java.util.Optional;

public interface UserRepository {
    User update(User user) throws GymException;
    Optional<User> findById(Long id);
    boolean isUsernameExist(String username);
    Optional<User> findByUsername(String username);
}
