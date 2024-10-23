package com.krasnopolskyi.service;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepo;

    @InjectMocks
    private TrainingTypeService trainingTypeService;

    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingType = new TrainingType(1, "Cardio");
    }

    @Test
    public void testFindById_Success() throws EntityException {
        // Mock the repository to return the trainingType when findById is called
        when(trainingTypeRepo.findById(trainingType.getId())).thenReturn(Optional.of(trainingType));

        // Call the service method
        TrainingType result = trainingTypeService.findById(trainingType.getId());

        // Assert the result is the same as the mocked trainingType
        assertEquals(trainingType, result);

        // Verify that the repository's findById method was called once
        verify(trainingTypeRepo, times(1)).findById(trainingType.getId());
    }

    @Test
    public void testFindById_NotFound() {
        // Mock the repository to return an empty Optional when the ID is not found
        when(trainingTypeRepo.findById(trainingType.getId())).thenReturn(Optional.empty());

        // Call the service method and expect an EntityNotFoundException
        EntityException exception = assertThrows(EntityException.class, () -> {
            trainingTypeService.findById(trainingType.getId());
        });

        // Assert the exception message is correct
        assertEquals("Could not find training type with id " + trainingType.getId(), exception.getMessage());

        // Verify that the repository's findById method was called once
        verify(trainingTypeRepo, times(1)).findById(trainingType.getId());
    }
}
