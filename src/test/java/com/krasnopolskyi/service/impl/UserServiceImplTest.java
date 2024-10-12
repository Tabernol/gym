package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.UserRepository;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.utils.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a sample UserDto object
        userDto = new UserDto("John", "Doe");

        // Set up a sample User object
        user = user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("123")
                .isActive(true)
                .build();
    }

    @Test
    public void testSave_Success() throws ValidateException {
        // Mock the username generation and user repository save method
        when(usernameGenerator.generateUsername(userDto.getFirstName(), userDto.getLastName())).thenReturn("johndoe");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.builder().id(1L).build(); // Simulate ID generation
            return savedUser;
        });

        User savedUser = userService.create(userDto);

        // Assert that the saved user is not null and has the expected properties
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("johndoe", savedUser.getUsername());
        assertNotNull(savedUser.getId());
        assertTrue(savedUser.getIsActive());

        // Verify that the necessary methods were called
        verify(usernameGenerator, times(1)).generateUsername("John", "Doe");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindById_Success() throws EntityNotFoundException {
        // Mock the user repository to return the user object
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        // Assert that the retrieved user matches the expected user
        assertEquals(user, result);

        // Verify that findById() was called once with the correct ID
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        // Mock the user repository to return empty when the user is not found
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that an EntityNotFoundException is thrown
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("Could not found user with id 1", thrown.getMessage());

        // Verify that findById() was called once with the correct ID
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate_Success() {
        // Mock the user repository to return the updated user object
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(user);

        // Assert that the updated user matches the expected user
        assertEquals(user, updatedUser);

        // Verify that save() was called once with the user object
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDelete_Success() {
        // Mock the delete method to return true
        when(userRepository.delete(user)).thenReturn(true);

        boolean result = userService.delete(user);

        // Assert that the result is true
        assertTrue(result);

        // Verify that delete() was called once with the user object
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDelete_Failure() {
        // Mock the delete method to return false
        when(userRepository.delete(user)).thenReturn(false);

        boolean result = userService.delete(user);

        // Assert that the result is false
        assertFalse(result);

        // Verify that delete() was called once with the user object
        verify(userRepository, times(1)).delete(user);
    }
}
