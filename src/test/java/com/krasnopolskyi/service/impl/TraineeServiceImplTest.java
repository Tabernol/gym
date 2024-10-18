package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.impl.TraineeRepositoryImpl;
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
    private TraineeRepositoryImpl traineeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private TraineeDto traineeDto;
    private Trainee trainee = new Trainee();
    private User user = new User();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        traineeDto = TraineeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Street")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build();

        user.setId(1L);
        user.setUsername("john.doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("123");

        trainee.setId(1L);
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setAddress(traineeDto.getAddress());
        trainee.setUser(user);
    }

    @Test
    public void testSave() throws ValidateException {
        // Mock the user service to return the user when saving
        when(userService.create(any(UserDto.class))).thenReturn(user);
        // Mock the trainee repository to return the saved trainee
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        TraineeResponseDto result = traineeService.save(traineeDto);

        // Assert that the returned TraineeResponseDto matches the user and trainee details
        assertEquals(traineeDto.getFirstName(), result.firstName());
        assertEquals(traineeDto.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(trainee.getAddress(), result.address());
        assertEquals(trainee.getDateOfBirth(), result.dateOfBirth());

        // Verify that userService.save() was called with the correct UserDto
        verify(userService, times(1)).create(any(UserDto.class));
        // Verify that traineeRepository.save() was called with the correct Trainee
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    public void testFindById_Success() throws EntityException {
        // Mock the trainee repository to return the trainee when queried by ID
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        when(userService.findById(trainee.getUser().getId())).thenReturn(user);

        TraineeResponseDto result = traineeService.findById(1L);

        // Assert that the returned TraineeResponseDto matches the user and trainee details
        assertEquals(user.getFirstName(), result.firstName());
        assertEquals(user.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(trainee.getAddress(), result.address());
        assertEquals(trainee.getDateOfBirth(), result.dateOfBirth());

        // Verify that findById() was called once with the correct ID
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        // Mock the trainee repository to return empty when the trainee is not found
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that an EntityNotFoundException is thrown
        EntityException thrown = assertThrows(EntityException.class, () -> {
            traineeService.findById(1L);
        });

        assertEquals("Could not found trainee with id 1", thrown.getMessage());

        // Verify that findById() was called once with the correct ID
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate() throws EntityException {
        // Mock the trainee repository to return the saved trainee
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        when(userService.findById(trainee.getUser().getId())).thenReturn(user);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
//        when(userService.update(any(User.class))).thenReturn(user);

        TraineeDto updatedDto = TraineeDto.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .address("456 Street")
                .dateOfBirth(LocalDate.of(2000, 5, 5))
                .build();

        TraineeResponseDto result = traineeService.update(updatedDto);

        // Assert that the returned TraineeResponseDto matches the updated trainee and user details
        assertEquals(updatedDto.getFirstName(), result.firstName());
        assertEquals(updatedDto.getLastName(), result.lastName());
        assertEquals(user.getUsername(), result.username());
        assertEquals(updatedDto.getAddress(), result.address());
        assertEquals(updatedDto.getDateOfBirth(), result.dateOfBirth());

        // Verify the calls to save trainee and update user
        verify(traineeRepository, times(1)).save(any(Trainee.class));
//        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    public void testDelete() throws EntityException {
        // Mock the trainee repository to return the trainee when found
        when(traineeRepository.findByUsername(any(String.class))).thenReturn(Optional.of(trainee));
        // Mock the trainee repository to return true when deleting
        when(traineeRepository.delete(trainee)).thenReturn(true);

        boolean result = traineeService.delete("john.doe");

        // Assert that the trainee is deleted successfully
        assertTrue(result);

        // Verify that traineeRepository.delete() was called once with the correct Trainee
        verify(traineeRepository, times(1)).delete(trainee);
    }
}
