package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.Training;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingRepositoryTest {

    @InjectMocks
    private TrainingRepository trainingRepository;

    private Training training;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        training = Training.builder().id(1L).build();

    }

//    @Test
//    public void testSave() {
//        // Mock the map to return null on first put, then return the saved training on second put
//        when(trainingsMap.put(training.getId(), training)).thenReturn(null).thenReturn(training);
//
//        Training savedTraining = trainingRepository.save(training);
//
//        // Verify that the training was saved twice in the map
//        verify(trainingsMap, times(2)).put(training.getId(), training);
//
//        // Assert that the saved training is the one we saved in the second call
//        assertEquals(training, savedTraining);
//    }
//
//    @Test
//    public void testFindById() {
//        // Mock the map to return the training when queried by ID using getOrDefault
//        when(trainingsMap.getOrDefault(training.getId(), null)).thenReturn(training);
//
//        Optional<Training> result = trainingRepository.findById(training.getId());
//
//        // Verify that the training is retrieved correctly
//        assertTrue(result.isPresent());
//        assertEquals(training, result.get());
//    }
}
