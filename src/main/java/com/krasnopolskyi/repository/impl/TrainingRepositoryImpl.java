package com.krasnopolskyi.repository.impl;


import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.repository.TrainingRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<Training> getFilteredTrainings(TrainingFilterDto filter) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        var cq = cb.createQuery(Training.class);
        var training = cq.from(Training.class);
        var trainee = training.join(Training_.trainee);
        var trainer = training.join(Training_.trainer);
        var traineeUser = trainee.join(Trainee_.user);
        var trainerUser = trainer.join(Trainer_.user);

        List<Predicate> predicates = new ArrayList<>();
        // Create predicates for both user tables
        if (filter.getOwner() != null) {
            Predicate ownerPredicate = cb.equal(traineeUser.get(User_.username), filter.getOwner());
            Predicate partnerPredicate = cb.equal(trainerUser.get(User_.username), filter.getOwner());
            predicates.add(cb.or(ownerPredicate, partnerPredicate)); // Combine with OR
        }
        if (filter.getPartner() != null) {
            Predicate partnerPredicate = cb.equal(traineeUser.get(User_.username), filter.getPartner());
            Predicate trainerPredicate = cb.equal(trainerUser.get(User_.username), filter.getPartner());
            predicates.add(cb.or(partnerPredicate, trainerPredicate)); // Combine with OR
        }

        if (filter.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(training.get(Training_.date), filter.getStartDate()));
        }
        if (filter.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(training.get(Training_.date), filter.getEndDate()));
        }
        if (filter.getTrainingType() != null) {
            predicates.add(cb.equal(training.get(Training_.trainingType).get(TrainingType_.type), filter.getTrainingType()));
        }

        cq.select(training).where(predicates.toArray(Predicate[]::new));

        // Execute query
        return session.createQuery(cq).getResultList();
    }
}
