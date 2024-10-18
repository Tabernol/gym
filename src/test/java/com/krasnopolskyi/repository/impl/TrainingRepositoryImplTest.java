package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.entity.Training;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.*;
import org.hibernate.query.criteria.spi.HibernateCriteriaBuilderDelegate;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.from.SqmAttributeJoin;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.hibernate.query.criteria.HibernateCriteriaBuilder; // Ensure you import the correct Hibernate CriteriaBuilder
import org.hibernate.query.criteria.JpaCriteriaQuery; // Import the correct JPA CriteriaQuery
import jakarta.persistence.criteria.CriteriaQuery; // Import the correct JPA CriteriaQuery

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainingRepositoryImplTest {

    @InjectMocks
    private TrainingRepositoryImpl trainingRepository;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private HibernateCriteriaBuilder criteriaBuilder;

    @Mock
    private SqmSelectStatement<Training> criteriaQuery;

    @Mock
    private SqmRoot<Training> root;
    @Mock
    private SqmAttributeJoin<Object, Object> join;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Ensure the mock session returns the correct type
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        // Mock the HibernateCriteriaBuilder explicitly
        HibernateCriteriaBuilder hibernateCriteriaBuilder = mock(HibernateCriteriaBuilder.class);
        when(session.getCriteriaBuilder()).thenReturn(hibernateCriteriaBuilder);

        // Mock the CriteriaQuery as JpaCriteriaQuery
        JpaCriteriaQuery<Training> criteriaQuery = mock(JpaCriteriaQuery.class);
        when(hibernateCriteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
    }

    @Test
    void testFindById() {
        Training training = new Training();
        training.setId(1L);
        when(session.get(Training.class, 1L)).thenReturn(training);

        Optional<Training> result = trainingRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }

    @Test
    void testFindById_NotFound() {
        when(session.get(Training.class, 1L)).thenReturn(null);

        Optional<Training> result = trainingRepository.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        Training training = new Training();
        training.setId(1L);
//        when(session.persist(training)).thenReturn(training);

        Training result = trainingRepository.save(training);

        assertEquals(training, result);
        verify(session).persist(training);
    }

    @Test
    void testGetFilteredTrainings_OnlyOwnerFilter() {
        // Setup filter
        TrainingFilterDto filter = new TrainingFilterDto();
        filter.setOwner("owner_username");

        // Mock entities and list
        Training training = new Training();
        List<Training> trainingList = new ArrayList<>();
        trainingList.add(training);

        // Mock session and criteria-related classes
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);

        // Mock joins: trainee, trainer, and user
        SqmAttributeJoin<Object, Object> traineeJoin = mock(SqmAttributeJoin.class);
        SqmAttributeJoin<Object, Object> trainerJoin = mock(SqmAttributeJoin.class);
        SqmAttributeJoin<Object, Object> userJoin = mock(SqmAttributeJoin.class);

        when(root.join("trainee")).thenReturn(traineeJoin);
        when(root.join("trainer")).thenReturn(trainerJoin);
        when(traineeJoin.join("user")).thenReturn(userJoin);

        // Mock the condition: user.get("username") equals filter.getOwner()
        when(userJoin.get("username")).thenReturn(mock(SqmPath.class));

        // Mock CriteriaQuery.select() and CriteriaQuery.where()
        when(criteriaQuery.select(any(SqmRoot.class))).thenReturn(criteriaQuery); // Ensure select() doesn't return null
        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery); // Ensure where() returns criteriaQuery

        // Mock creating a query and returning a list
        org.hibernate.query.Query<Training> query = mock(org.hibernate.query.Query.class);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(trainingList);

        // Call the method
        List<Training> result = trainingRepository.getFilteredTrainings(filter);

        // Assertions
        assertEquals(1, result.size());
        assertEquals(training, result.get(0));

        // Verify the interactions with CriteriaBuilder
        verify(criteriaBuilder, times(1)).equal(any(), eq("owner_username"));
        verify(criteriaQuery, times(1)).from(Training.class);
        verify(session, times(1)).createQuery(criteriaQuery);
    }
}
