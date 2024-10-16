package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Override
    public User update(User user) throws GymException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        return null;
    }

    public Optional<User> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }

    public boolean isUsernameExist(String username) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT count(*) FROM users u WHERE u.username = :username";

        NativeQuery<Long> nativeQuery = session.createNativeQuery(sql, Long.class);
        nativeQuery.setParameter("username", username);

        Long count = nativeQuery.uniqueResult();

        return count > 0;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT * FROM users WHERE username = :username";

        NativeQuery<User> nativeQuery = session.createNativeQuery(sql, User.class);
        nativeQuery.setParameter("username", username);
        return nativeQuery.getResultList().stream().findFirst();
    }
}
