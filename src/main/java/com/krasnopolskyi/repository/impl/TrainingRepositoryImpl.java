package com.krasnopolskyi.repository.impl;


import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.repository.TrainingRepository;
import com.krasnopolskyi.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {

    private final SessionFactory sessionFactory;

    private final UserRepository userRepository;

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
    public List<Training> getFilteredTrainings(TrainingFilterDto filter) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        var cq = cb.createQuery(Training.class);
        var training = cq.from(Training.class);

        // Joins for the trainee and trainer
        var trainee = training.join("trainee");
        var trainer = training.join("trainer");
        var user = trainee.join("user");


        List<Predicate> predicates = new ArrayList<>();

        // Add predicates dynamically based on filter values
        if (filter.getOwner() != null) {
            predicates.add(cb.equal(user.get("username"), filter.getOwner())); // currently I here
        }
        if (filter.getPartner() != null) {
            predicates.add(cb.equal(user.get("username"), filter.getPartner()));
        }
        if (filter.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(training.get("date"), filter.getStartDate()));
        }
        if (filter.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(training.get("date"), filter.getEndDate()));
        }
        if(filter.getTrainingType() != null){
            predicates.add(cb.equal(training.get("trainingType").get("type"), filter.getTrainingType()));
        }

        cq.select(training).where(predicates.toArray(Predicate[]::new));
        Hibernate.initialize(training.get("trainer"));
        Hibernate.initialize(training.get("trainee"));

        // Execute query
        return session.createQuery(cq).getResultList();
    }
}
