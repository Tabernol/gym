package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.impl.TrainerRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock
    private TrainerRepositoryImpl trainerRepository;

    @Mock
    private UserService userService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerService trainerService;

    private TrainerDto trainerDto;
    private Trainer trainer;
    private User user = new User();
    private TrainingType trainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        trainerDto = TrainerDto.builder().firstName("John").lastName("Doe").specialization(1).build();

        user.setId(1L);
        user.setUsername("john.doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("123");

        trainingType = new TrainingType(1, "Cardio");

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setSpecialization(trainingType);
    }

    @Test
    public void testSave_Success() throws GymException {
        // Mock trainingTypeService to return the training type
        when(trainingTypeService.findById(1)).thenReturn(trainingType);
        // Mock userService to return the user
        when(userService.create(any(UserDto.class))).thenReturn(user);
        // Mock trainerRepository to save and return the trainer
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerResponseDto result = trainerService.save(trainerDto);

        // Assert that the returned TrainerResponseDto matches the user and specialization details
        assertEquals(user.getFirstName(), result.firstName());
        assertEquals(user.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(trainingType.getType(), result.specialization());

        // Verify interactions
        verify(userService, times(1)).create(any(UserDto.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    public void testSave_TrainingTypeNotFound() throws EntityException, ValidateException {
        // Mock trainingTypeService to throw EntityNotFoundException
        when(trainingTypeService.findById(1)).thenThrow(new EntityException("Not found"));

        // Assert that ValidateException is thrown when trying to save
        ValidateException thrown = assertThrows(ValidateException.class, () -> {
            trainerService.save(trainerDto);
        });

        assertEquals("Specialisation with id 1 does not exist", thrown.getMessage());

        // Verify that userService.save() and trainerRepository.save() were not called
        verify(userService, never()).create(any(UserDto.class));
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    public void testFindById_Success() throws EntityException {
        // Mock the trainer repository to return the trainer when queried by ID
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(userService.findById(trainer.getUser().getId())).thenReturn(user);
        when(trainingTypeService.findById(trainer.getSpecialization().getId())).thenReturn(trainingType);

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
        EntityException thrown = assertThrows(EntityException.class, () -> {
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
        when(userService.findById(trainer.getUser().getId())).thenReturn(user);
        when(trainingTypeService.findById(trainer.getSpecialization().getId())).thenReturn(trainingType);

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

    @Test
    void findByUsernameThrowsException() {
        String username = "testUser";
        when(trainerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThrows(EntityException.class, ()-> trainerService.findByUsername("test"));
    }

    @Test
    void testFindByUsernameSuccess() throws EntityException {
        String username = "testUser";
        when(trainerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(trainer));

        TrainerResponseDto result = trainerService.findByUsername(username);

        assertNotNull(result);
    }
}
