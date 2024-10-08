package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TrainingTypeRepository;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepo;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingType = new TrainingType(1, "Cardio");
    }

    @Test
    public void testFindById_Success() throws EntityNotFoundException {
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
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            trainingTypeService.findById(trainingType.getId());
        });

        // Assert the exception message is correct
        assertEquals("Could not find training type with id " + trainingType.getId(), exception.getMessage());

        // Verify that the repository's findById method was called once
        verify(trainingTypeRepo, times(1)).findById(trainingType.getId());
    }
}
