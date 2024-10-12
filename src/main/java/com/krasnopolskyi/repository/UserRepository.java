package com.krasnopolskyi.repository;

import com.krasnopolskyi.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);
    Optional<User> findById(Long id);
    boolean isUsernameExist(String username);
}
