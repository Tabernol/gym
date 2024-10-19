package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingTypeRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TrainingTypeRepositoryImpl trainingTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testFindById_TrainingTypeExists() {
        // Arrange
        Integer trainingTypeId = 1;
        TrainingType trainingType = new TrainingType(trainingTypeId, "Strength");

        when(session.get(TrainingType.class, trainingTypeId)).thenReturn(trainingType);

        // Act
        Optional<TrainingType> foundTrainingType = trainingTypeRepository.findById(trainingTypeId);

        // Assert
        assertTrue(foundTrainingType.isPresent());
        assertEquals(trainingTypeId, foundTrainingType.get().getId());
        assertEquals("Strength", foundTrainingType.get().getType());
        verify(session, times(1)).get(TrainingType.class, trainingTypeId);
    }

    @Test
    void testFindById_TrainingTypeDoesNotExist() {
        // Arrange
        Integer trainingTypeId = 1;

        when(session.get(TrainingType.class, trainingTypeId)).thenReturn(null);

        // Act
        Optional<TrainingType> foundTrainingType = trainingTypeRepository.findById(trainingTypeId);

        // Assert
        assertFalse(foundTrainingType.isPresent());
        verify(session, times(1)).get(TrainingType.class, trainingTypeId);
    }
}
