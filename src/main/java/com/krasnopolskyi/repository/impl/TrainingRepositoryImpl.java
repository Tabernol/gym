package com.krasnopolskyi.repository.impl;


import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {

    private final SessionFactory sessionFactory;

    public Optional<Training> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, id);
        return Optional.ofNullable(training);
    }

    @Override
    public Training save(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(training);
        return training;
    }

    @Override
    public List<Training> findAllByUsername(String username) {
        return null;


    }
}
