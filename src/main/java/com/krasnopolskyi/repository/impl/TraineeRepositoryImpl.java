package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.entity.Trainee;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class TraineeRepositoryImpl implements TraineeRepository {

    private final SessionFactory sessionFactory;

    public Optional<Trainee> save(Trainee trainee) {
        return Optional.ofNullable(trainee);
    }
    @Transactional(readOnly = true)
    public Optional<Trainee> findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Trainee trainee = session.get(Trainee.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable( trainee);
    }

    public boolean delete(Trainee trainee) {
        return false;
    }
}
