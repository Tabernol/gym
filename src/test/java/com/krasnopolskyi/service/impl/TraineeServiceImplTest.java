package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TraineeRepository;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private TraineeDto traineeDto;
    private Trainee trainee;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        traineeDto = TraineeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Street")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build();

        user = User.builder().login("john.doe").password("123").build();


        trainee = Trainee.builder()
                .id(1L)
                .userId(user.getId())
                .address(traineeDto.getAddress())
                .dateOfBirth(traineeDto.getDateOfBirth())
                .build();
    }

    @Test
    public void testSave() {
        // Mock the user service to return the user when saving
        when(userService.save(any(UserDto.class))).thenReturn(user);
        // Mock the trainee repository to return the saved trainee
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        UserCredentials result = traineeService.save(traineeDto);

        // Assert that the returned UserCredentials match the user details
        assertEquals(user.getLogin(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());

        // Verify that userService.save() was called with the correct UserDto
        verify(userService, times(1)).save(any(UserDto.class));
        // Verify that traineeRepository.save() was called with the correct Trainee
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    public void testFindById_Success() throws EntityNotFoundException {
        // Mock the trainee repository to return the trainee when queried by ID
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.findById(1L);

        // Assert that the trainee is retrieved correctly
        assertEquals(trainee, result);

        // Verify that findById() was called once with the correct ID
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        // Mock the trainee repository to return empty when the trainee is not found
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that an EntityNotFoundException is thrown
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            traineeService.findById(1L);
        });

        assertEquals("Could not found trainee with id 1", thrown.getMessage());

        // Verify that findById() was called once with the correct ID
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        // Mock the trainee repository to return the saved trainee
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        Trainee result = traineeService.update(trainee);

        // Assert that the trainee is updated correctly
        assertEquals(trainee, result);
        assertEquals(trainee.getAddress(), result.getAddress());
        assertEquals(trainee.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(trainee.getUserId(), result.getUserId());

        // Verify that traineeRepository.save() was called once with the correct Trainee
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    public void testDelete() {
        // Mock the trainee repository to return true when the trainee is deleted
        when(traineeRepository.delete(trainee)).thenReturn(true);

        boolean result = traineeService.delete(trainee);

        // Assert that the trainee is deleted successfully
        assertTrue(result);

        // Verify that traineeRepository.delete() was called once with the correct Trainee
        verify(traineeRepository, times(1)).delete(trainee);
    }
}
