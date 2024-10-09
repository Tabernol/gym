package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingTypeRepositoryTest {


    @Mock
    private Map<Integer, TrainingType> trainingTypesMap;

    @InjectMocks
    private TrainingTypeRepository trainingTypeRepository;

    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingType = new TrainingType(1, "Cardio");
    }

//    @Test
//    public void testFindById_Success() {
//        // Mock the map to return the trainingType when queried by ID
//        when(trainingTypesMap.getOrDefault(trainingType.getId(), null)).thenReturn(trainingType);
//
//        Optional<TrainingType> result = trainingTypeRepository.findById(trainingType.getId());
//
//        // Assert that the result contains the expected training type
//        assertTrue(result.isPresent());
//        assertEquals(trainingType, result.get());
//
//        // Verify that getOrDefault was called once with the correct ID
//        verify(trainingTypesMap, times(1)).getOrDefault(trainingType.getId(), null);
//    }
//
//    @Test
//    public void testFindById_NotFound() {
//        // Mock the map to return null when the ID is not found
//        when(trainingTypesMap.getOrDefault(trainingType.getId(), null)).thenReturn(null);
//
//        Optional<TrainingType> result = trainingTypeRepository.findById(trainingType.getId());
//
//        // Assert that the result is empty
//        assertFalse(result.isPresent());
//
//        // Verify that getOrDefault was called once with the correct ID
//        verify(trainingTypesMap, times(1)).getOrDefault(trainingType.getId(), null);
//    }
}
