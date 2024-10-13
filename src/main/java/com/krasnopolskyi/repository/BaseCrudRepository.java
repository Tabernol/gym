package com.krasnopolskyi.repository;

import java.util.Optional;

public interface BaseCrudRepository <T>{

    T save(T t);
    Optional<T> findById(Long id);
    Optional<T> findByUsername(String username);
}
