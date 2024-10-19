package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.Trainee;
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

class TraineeRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TraineeRepositoryImpl traineeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testSave() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        // Act
        Trainee savedTrainee = traineeRepository.save(trainee);

        // Assert
        assertEquals(trainee, savedTrainee);
        verify(session, times(1)).persist(trainee);  // Ensure that session.persist() is called once
    }

    @Test
    void testFindById_TraineeExists() {
        // Arrange
        Long traineeId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);

        when(session.get(Trainee.class, traineeId)).thenReturn(trainee);

        // Act
        Optional<Trainee> foundTrainee = traineeRepository.findById(traineeId);

        // Assert
        assertTrue(foundTrainee.isPresent());
        assertEquals(traineeId, foundTrainee.get().getId());
    }

    @Test
    void testFindById_TraineeDoesNotExist() {
        // Arrange
        Long traineeId = 1L;

        when(session.get(Trainee.class, traineeId)).thenReturn(null);

        // Act
        Optional<Trainee> foundTrainee = traineeRepository.findById(traineeId);

        // Assert
        assertFalse(foundTrainee.isPresent());
    }

    @Test
    void testFindByUsername_TraineeExists() {
        // Arrange
        String username = "test_user";
        String sql = "SELECT t.* FROM trainee t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = :username";

        NativeQuery<Trainee> queryMock = mock(NativeQuery.class);
        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername(username);
        trainee.setUser(user);

        when(session.createNativeQuery(sql, Trainee.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(trainee));

        // Act
        Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);

        // Assert
        assertTrue(foundTrainee.isPresent());
        assertEquals(username, foundTrainee.get().getUser().getUsername());
    }

    @Test
    void testFindByUsername_TraineeDoesNotExist() {
        // Arrange
        String username = "non_existing_user";
        String sql = "SELECT t.* FROM trainee t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = :username";

        NativeQuery<Trainee> queryMock = mock(NativeQuery.class);

        when(session.createNativeQuery(sql, Trainee.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of());

        // Act
        Optional<Trainee> foundTrainee = traineeRepository.findByUsername(username);

        // Assert
        assertFalse(foundTrainee.isPresent());
    }

    @Test
    void testDelete() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        // Act
        boolean result = traineeRepository.delete(trainee);

        // Assert
        assertTrue(result);
        verify(session, times(1)).remove(trainee);  // Ensure that session.remove() is called once
    }
}
