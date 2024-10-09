package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.database.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private Storage storage;

    // initialized via setter because task condition 4
    // I prefer initialized via constructor
    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public User save(User user) {
        // need to save into map twice because when do it at first time Map returns null
        storage.getUsers().put(user.getId(), user);
        return storage.getUsers().put(user.getId(), user); // saving to a file is done implicitly using AOP
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.getUsers().getOrDefault(id, null));
    }

    public User update(User user) {
        return storage.getUsers().put(user.getId(), user);
    }

    public boolean delete(User user) {
        return storage.getUsers().remove(user.getId(), user);
    } // deleting to a file is done implicitly using AOP

    public boolean isUsernameExist(String username) {
        for (User user : storage.getUsers().values()) {
            if (username.equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }
}
