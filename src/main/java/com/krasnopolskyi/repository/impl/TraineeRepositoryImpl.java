package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.entity.Trainee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TraineeRepositoryImpl implements TraineeRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Trainee save(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainee);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, id);
        return Optional.ofNullable(trainee);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT t.* FROM trainee t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = :username";

        NativeQuery<Trainee> nativeQuery = session.createNativeQuery(sql, Trainee.class);
        nativeQuery.setParameter("username", username);
        return nativeQuery.getResultList().stream().findFirst();
    }

    @Override
    public boolean delete(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(trainee);
        return true;
    }
}
