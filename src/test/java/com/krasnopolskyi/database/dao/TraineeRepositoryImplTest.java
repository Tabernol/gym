package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.repository.impl.TraineeRepositoryImpl;
import com.krasnopolskyi.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class TraineeRepositoryImplTest {

    @InjectMocks
    private TraineeRepositoryImpl traineeRepositoryImpl;

    private Trainee trainee;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and inject them into the repository
        MockitoAnnotations.openMocks(this);
        // Mock storage behavior
        // Create a dummy Trainee object for testing

    }

//    @Test
//    public void testSaveTrainee() {
//        // Mock saving behavior: first call returns null, second call returns the trainee
//        when(traineesMap.put(trainee.getId(), trainee)).thenReturn(null).thenReturn(trainee);
//
//        Trainee savedTrainee = traineeRepository.save(trainee);
//
//        // Verify that the trainee is saved twice
//        verify(traineesMap, times(2)).put(trainee.getId(), trainee);
//        assertEquals(trainee, savedTrainee);
//    }
//
//    @Test
//    public void testFindById() {
//        // Mock the map to return the trainee when queried by ID
//        when(traineesMap.getOrDefault(trainee.getId(), null)).thenReturn(trainee);
//
//        Optional<Trainee> result = traineeRepository.findById(trainee.getId());
//
//        // Verify that the trainee is retrieved correctly
//        assertTrue(result.isPresent());
//        assertEquals(trainee, result.get());
//    }
//
//    @Test
//    public void testFindById_NotFound() {
//        // Mock the map to return null when trainee is not found
//        when(traineesMap.get(trainee.getId())).thenReturn(null);
//
//        Optional<Trainee> result = traineeRepository.findById(trainee.getId());
//
//        // Verify that no trainee is found
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void testDeleteTrainee() {
//        // Mock successful removal
//        when(traineesMap.remove(trainee.getId(), trainee)).thenReturn(true);
//
//        boolean result = traineeRepository.delete(trainee);
//
//        // Verify the trainee is removed
//        verify(traineesMap).remove(trainee.getId(), trainee);
//        assertTrue(result);
//    }
//
//    @Test
//    public void testDeleteTrainee_NotFound() {
//        // Mock unsuccessful removal
//        when(traineesMap.remove(trainee.getId(), trainee)).thenReturn(false);
//
//        boolean result = traineeRepository.delete(trainee);
//
//        // Verify the trainee was not removed
//        verify(traineesMap).remove(trainee.getId(), trainee);
//        assertFalse(result);
//    }
}
