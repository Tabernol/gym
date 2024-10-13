package com.krasnopolskyi.repository.impl;


import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.repository.TrainingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {

    public Training save(Training training) {
        return training;
    }

    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(null);
    }

    @Override
    public Training save(TrainingDto trainingDto) {
        return null;
    }

    @Override
    public List<Training> findAllByUsername(String username) {
        return null;
    }
}
