package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.entity.Trainee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TraineeRepositoryImpl implements TraineeRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Trainee save(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainee.getUser());
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
        String query = "SELECT t.id AS trainee_id, u.id AS u_id, first_name, last_name FROM trainee t " +
                "JOIN users u ON t.user_id = u.id WHERE u.username LIKE :username;";

        NativeQuery<Trainee> nativeQuery = session.createNativeQuery(query, Trainee.class);
        nativeQuery.setParameter("username", username);
        Optional<Trainee> trainee = nativeQuery.getResultList().stream().findFirst();

        return trainee;
    }

    @Override
    public boolean delete(String username) {
//        Session session = sessionFactory.getCurrentSession();
//
//        Trainee traineeFromDb = session.get(Trainee.class, trainee.getId());
//        if (traineeFromDb == null) {
//            return false; // Trainee not found
//        }
//        session.remove(traineeFromDb);
        return true;
    }
}
