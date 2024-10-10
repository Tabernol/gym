package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public Optional<User> save(User user) {
        return Optional.ofNullable(user);
    }

    public Optional<User> findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable( user);
    }

    public User update(User user) {
        return user;
    }

    public boolean delete(User user) {
        return false;
    }

    public boolean isUsernameExist(String username) {
        return false;
    }
}
