package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {
    private final SessionFactory sessionFactory;
    @Override
    public Optional<TrainingType> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        TrainingType trainingType = session.get(TrainingType.class, id);
        return Optional.ofNullable(trainingType);
    }
}
