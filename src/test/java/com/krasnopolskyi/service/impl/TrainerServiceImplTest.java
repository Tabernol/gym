package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TrainerRepository;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.GymException;
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

        trainerDto = TrainerDto.builder().firstName("Jane").lastName("Doe").specialization(1).build();

        user = User.builder()
                .id(1L)
                .username("jane.doe")
                .firstName("Jane")
                .lastName("Doe")
                .password("123")
                .build();

        trainingType = new TrainingType(1, "Cardio");

        trainer = Trainer.builder()
                .id(1L)
                .userId(user.getId())
                .specialization(trainingType.getId())
                .build();
    }

    @Test
    public void testSave_Success() throws GymException {
        // Mock trainingTypeService to return the training type
        when(trainingTypeService.findById(1)).thenReturn(trainingType);
        // Mock userService to return the user
        when(userService.save(any(UserDto.class))).thenReturn(user);
        // Mock trainerRepository to save and return the trainer
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerResponseDto result = trainerService.save(trainerDto);

        // Assert that the returned TrainerResponseDto matches the user and specialization details
        assertEquals(user.getFirstName(), result.firstName());
        assertEquals(user.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(trainingType.getType(), result.specialization());

        // Verify interactions
        verify(userService, times(1)).save(any(UserDto.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    public void testSave_TrainingTypeNotFound() throws EntityNotFoundException, ValidateException {
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
        when(userService.findById(trainer.getUserId())).thenReturn(user);
        when(trainingTypeService.findById(trainer.getSpecialization())).thenReturn(trainingType);

        TrainerResponseDto result = trainerService.findById(1L);

        // Assert that the TrainerResponseDto is mapped correctly
        assertEquals(user.getFirstName(), result.firstName());
        assertEquals(user.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(trainingType.getType(), result.specialization());

        // Verify that findById() was called once
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
    public void testUpdate_Success() throws GymException {
        // Mock the trainer repository to return the existing trainer
        when(trainerRepository.findById(trainerDto.getId())).thenReturn(Optional.of(trainer));
        when(userService.findById(trainer.getUserId())).thenReturn(user);
        when(trainingTypeService.findById(trainer.getSpecialization())).thenReturn(trainingType);

        // Mock the repository to return the updated trainer
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerResponseDto result = trainerService.update(trainerDto);

        // Assert that the TrainerResponseDto is mapped correctly
        assertEquals(user.getFirstName(), result.firstName());
        assertEquals(user.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(trainingType.getType(), result.specialization());

        // Verify interactions
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }
}
