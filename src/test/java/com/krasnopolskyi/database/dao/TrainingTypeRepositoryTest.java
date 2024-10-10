package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.service.impl.TrainingTypeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

class TrainingTypeRepositoryTest {


    @Mock
    private Map<Integer, TrainingType> trainingTypesMap;

    @InjectMocks
    private TrainingTypeRepositoryImpl trainingTypeRepositoryImpl;

    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
