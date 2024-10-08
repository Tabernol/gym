package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TrainerRepository;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TrainingTypeService;
import com.krasnopolskyi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {
    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserService userService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private TrainerDto trainerDto;
    private Trainer trainer;
    private User user;
    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        trainerDto = new TrainerDto("Jane", "Doe", 1);

        user = User.builder().login("jane.doe").password("123").build();

        trainingType = new TrainingType(1, "Cardio");

        trainer = Trainer.builder()
                .id(1L)
                .userId(user.getId())
                .specialization(trainingType.getId())
                .build();
    }

    @Test
    public void testSave_Success() throws EntityNotFoundException, ValidateException {
        // Mock trainingTypeService to return the training type
        when(trainingTypeService.findById(1)).thenReturn(trainingType);
        // Mock userService to return the user
        when(userService.save(any(UserDto.class))).thenReturn(user);
        // Mock trainerRepository to return the saved trainer
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        UserCredentials result = trainerService.save(trainerDto);

        // Assert that the returned UserCredentials match the user details
        assertEquals(user.getLogin(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());

        // Verify that userService.save() and trainerRepository.save() were called correctly
        verify(userService, times(1)).save(any(UserDto.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    public void testSave_TrainingTypeNotFound() throws EntityNotFoundException {
        // Mock trainingTypeService to throw EntityNotFoundException
        when(trainingTypeService.findById(1)).thenThrow(new EntityNotFoundException("Not found"));

        // Assert that ValidateException is thrown when trying to save
        ValidateException thrown = assertThrows(ValidateException.class, () -> {
            trainerService.save(trainerDto);
        });

        assertEquals("Specialisation with id 1 does not exist", thrown.getMessage());

        // Verify that userService.save() and trainerRepository.save() were not called
        verify(userService, never()).save(any(UserDto.class));
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    public void testFindById_Success() throws EntityNotFoundException {
        // Mock the trainer repository to return the trainer when queried by ID
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.findById(1L);

        // Assert that the trainer is retrieved correctly
        assertEquals(trainer, result);

        // Verify that findById() was called once with the correct ID
        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        // Mock the trainer repository to return empty when the trainer is not found
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that an EntityNotFoundException is thrown
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            trainerService.findById(1L);
        });

        assertEquals("Could not found trainer with id 1", thrown.getMessage());

        // Verify that findById() was called once with the correct ID
        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        // Mock the trainer repository to return the saved trainer
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        Trainer result = trainerService.update(trainer);

        // Assert that the trainer is updated correctly
        assertEquals(trainer, result);

        // Verify that trainerRepository.save() was called once with the correct Trainer
        verify(trainerRepository, times(1)).save(trainer);
    }
}
