package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.entity.Trainee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class TraineeRepositoryImpl implements TraineeRepository {

    private final SessionFactory sessionFactory;

    @Transactional
    public Trainee save(Trainee trainee) {
        Session session = sessionFactory.openSession();
        session.persist(trainee.getUser());
        session.persist(trainee);
        return trainee;
    }

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
