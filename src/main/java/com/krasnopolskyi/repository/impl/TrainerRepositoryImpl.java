package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class TrainerRepositoryImpl implements TrainerRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Trainer save(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainer.getUser());
        session.persist(trainer);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Trainer trainer = session.get(Trainer.class, id);
        return Optional.ofNullable(trainer);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT t.* FROM trainer t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = :username";

        NativeQuery<Trainer> nativeQuery = session.createNativeQuery(sql, Trainer.class);
        nativeQuery.setParameter("username", username);
        return nativeQuery.getResultList().stream().findFirst();
    }
}
