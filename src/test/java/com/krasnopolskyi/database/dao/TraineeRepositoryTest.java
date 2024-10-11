package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeRepositoryTest {
    @Mock
    private Storage storage;
    @Mock
    private Map<Long, Trainee> traineesMap;

    @InjectMocks
    private TraineeRepository traineeRepository;

    private Trainee trainee;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and inject them into the repository
        MockitoAnnotations.openMocks(this);
        // Mock storage behavior
        when(storage.getTrainees()).thenReturn(traineesMap);

        // Create a dummy Trainee object for testing
        trainee = Trainee.builder().id(1L).build();
    }

    @Test
    public void testSaveTrainee() {
        // Mock saving behavior: first call returns null, second call returns the trainee
        when(traineesMap.get(trainee.getId())).thenReturn(trainee);

        Trainee savedTrainee = traineeRepository.save(trainee);

        // Verify that the trainee is saved twice
        verify(traineesMap, times(1)).put(trainee.getId(), trainee);
        assertEquals(trainee, savedTrainee);
    }

    @Test
    public void testFindById() {
        // Mock the map to return the trainee when queried by ID
        when(traineesMap.getOrDefault(trainee.getId(), null)).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.findById(trainee.getId());

        // Verify that the trainee is retrieved correctly
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    public void testFindById_NotFound() {
        // Mock the map to return null when trainee is not found
        when(traineesMap.get(trainee.getId())).thenReturn(null);

        Optional<Trainee> result = traineeRepository.findById(trainee.getId());

        // Verify that no trainee is found
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteTrainee() {
        // Mock successful removal
        when(traineesMap.remove(trainee.getId(), trainee)).thenReturn(true);

        boolean result = traineeRepository.delete(trainee);

        // Verify the trainee is removed
        verify(traineesMap).remove(trainee.getId(), trainee);
        assertTrue(result);
    }

    @Test
    public void testDeleteTrainee_NotFound() {
        // Mock unsuccessful removal
        when(traineesMap.remove(trainee.getId(), trainee)).thenReturn(false);

        boolean result = traineeRepository.delete(trainee);

        // Verify the trainee was not removed
        verify(traineesMap).remove(trainee.getId(), trainee);
        assertFalse(result);
    }
}
