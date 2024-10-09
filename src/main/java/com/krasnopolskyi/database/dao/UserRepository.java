package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    public User save(User user) {
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(null);
    }

    public User update(User user) {
        return user;
    }

    public boolean delete(User user) {
        return false;
    }

    public boolean isUsernameExist(String username) {
        return false;
    }
}
