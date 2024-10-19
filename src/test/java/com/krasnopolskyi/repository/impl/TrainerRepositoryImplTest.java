package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TrainerRepositoryImpl trainerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testSave() {
        // Arrange
        Trainer trainer = new Trainer();
        trainer.setId(1L);

        // Act
        Trainer savedTrainer = trainerRepository.save(trainer);

        // Assert
        assertEquals(trainer, savedTrainer);
        verify(session, times(1)).persist(trainer);  // Ensure that session.persist() is called once
    }

    @Test
    void testFindById_TrainerExists() {
        // Arrange
        Long trainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);

        when(session.get(Trainer.class, trainerId)).thenReturn(trainer);

        // Act
        Optional<Trainer> foundTrainer = trainerRepository.findById(trainerId);

        // Assert
        assertTrue(foundTrainer.isPresent());
        assertEquals(trainerId, foundTrainer.get().getId());
    }

    @Test
    void testFindById_TrainerDoesNotExist() {
        // Arrange
        Long trainerId = 1L;

        when(session.get(Trainer.class, trainerId)).thenReturn(null);

        // Act
        Optional<Trainer> foundTrainer = trainerRepository.findById(trainerId);

        // Assert
        assertFalse(foundTrainer.isPresent());
    }

    @Test
    void testFindByUsername_TrainerExists() {
        // Arrange
        String username = "test_user";
        String sql = "SELECT t.* FROM trainer t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = :username";

        NativeQuery<Trainer> queryMock = mock(NativeQuery.class);
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUsername(username);
        trainer.setUser(user);

        when(session.createNativeQuery(sql, Trainer.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(trainer));

        // Act
        Optional<Trainer> foundTrainer = trainerRepository.findByUsername(username);

        // Assert
        assertTrue(foundTrainer.isPresent());
        assertEquals(username, foundTrainer.get().getUser().getUsername());
    }

    @Test
    void testFindByUsername_TrainerDoesNotExist() {
        // Arrange
        String username = "non_existing_user";
        String sql = "SELECT t.* FROM trainer t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = :username";

        NativeQuery<Trainer> queryMock = mock(NativeQuery.class);

        when(session.createNativeQuery(sql, Trainer.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of());

        // Act
        Optional<Trainer> foundTrainer = trainerRepository.findByUsername(username);

        // Assert
        assertFalse(foundTrainer.isPresent());
    }

    @Test
    void testFindAll_TrainersExist() {
        // Arrange
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);

        when(session.createSelectionQuery("select t from Trainer t", Trainer.class))
                .thenReturn(mock(NativeQuery.class));
        when(session.createSelectionQuery("select t from Trainer t", Trainer.class)
                .getResultList()).thenReturn(List.of(trainer1, trainer2));

        // Act
        List<Trainer> trainers = trainerRepository.findAll();

        // Assert
        assertEquals(2, trainers.size());
        assertEquals(1L, trainers.get(0).getId());
        assertEquals(2L, trainers.get(1).getId());
    }

    @Test
    void testFindAll_NoTrainersExist() {
        // Arrange
        when(session.createSelectionQuery("select t from Trainer t", Trainer.class))
                .thenReturn(mock(NativeQuery.class));
        when(session.createSelectionQuery("select t from Trainer t", Trainer.class)
                .getResultList()).thenReturn(List.of());

        // Act
        List<Trainer> trainers = trainerRepository.findAll();

        // Assert
        assertTrue(trainers.isEmpty());
    }
}
