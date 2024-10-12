package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.repository.impl.TrainingRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingRepositoryTest {

    @Mock
    private Map<Long, Training> trainingsMap;

    @InjectMocks
    private TrainingRepositoryImpl trainingRepository;

    private Training training;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Training training = new Training();
        training.setId(1L);
        training.setTrainingName("Cardio");
        training.setTrainingType(1);
        training.setDate(LocalDate.of(2024, 9,3));
        training.setDuration(1000);
        training.setTraineeId(1L);
        training.setTrainerId(1L);

        // Mock the behavior of storage.getTrainings() to return the trainingsMap
//        when(storage.getTrainings()).thenReturn(trainingsMap);
    }

    @Test
    public void testSave() {
        // Mock the map to return null on first put, then return the saved training on second put
        when(trainingsMap.get(training.getId())).thenReturn(training);

        Training savedTraining = trainingRepository.save(training);

        // Verify that the training was saved twice in the map
        verify(trainingsMap, times(1)).put(training.getId(), training);

        // Assert that the saved training is the one we saved in the second call
        assertEquals(training, savedTraining);
        assertEquals(training.getTraineeId(), savedTraining.getTraineeId());
        assertEquals(training.getTrainerId(), savedTraining.getTrainerId());
        assertEquals(training.getTrainingType(), savedTraining.getTrainingType());
    }

    @Test
    public void testFindById() {
        // Mock the map to return the training when queried by ID using getOrDefault
        when(trainingsMap.getOrDefault(training.getId(), null)).thenReturn(training);

        Optional<Training> result = trainingRepository.findById(training.getId());

        // Verify that the training is retrieved correctly
        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }
}
