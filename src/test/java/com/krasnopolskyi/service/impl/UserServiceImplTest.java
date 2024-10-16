package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.UserRepository;
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
        user = user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("john.doe");
        user.setPassword("123");
        user.setIsActive(true);
    }

    @Test
    public void testFindById_Success() throws EntityException {
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
        EntityException thrown = assertThrows(EntityException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("Could not found user with id 1", thrown.getMessage());

        // Verify that findById() was called once with the correct ID
        verify(userRepository, times(1)).findById(1L);
    }
}
