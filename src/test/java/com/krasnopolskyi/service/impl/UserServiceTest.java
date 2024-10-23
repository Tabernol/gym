package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.entity.Role;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.UserRepository;
import com.krasnopolskyi.utils.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a sample UserDto object
        userDto = new UserDto("John", "Doe", Role.TRAINEE);

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

    @Test
    void testCreateUser_Success() throws ValidateException {
        // Given
        UserDto userDto = new UserDto("John", "Doe", Role.TRAINEE);
        String generatedUsername = "john.doe";
        String generatedPassword = "securePassword";

        mockStatic(PasswordGenerator.class);
        when(PasswordGenerator.generatePassword()).thenReturn(generatedPassword);

        // When
        User result = userService.create(userDto);

        // Then
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(generatedUsername, result.getUsername());
        assertEquals(generatedPassword, result.getPassword());
        assertTrue(result.getIsActive());
        PasswordGenerator.generatePassword();
    }

    @Test
    void testCheckCredentials_ValidCredentials() throws EntityException {
        // Given
        String username = "john.doe";
        String password = "securePassword";
        UserCredentials credentials = new UserCredentials(username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        boolean result = userService.checkCredentials(credentials);

        // Then
        assertTrue(result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testCheckCredentials_InvalidCredentials() throws EntityException {
        // Given
        String username = "john.doe";
        String password = "wrongPassword";
        UserCredentials credentials = new UserCredentials(username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword("securePassword"); // The password in the database is different

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        boolean result = userService.checkCredentials(credentials);

        // Then
        assertFalse(result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testCheckCredentials_UserNotFound() {
        // Given
        String username = "john.doe";
        UserCredentials credentials = new UserCredentials(username, "password");
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When
        EntityException exception = assertThrows(EntityException.class, () -> {
            userService.checkCredentials(credentials);
        });

        // Then
        assertEquals("Could not found user: " + username, exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void changePassword() throws GymException {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(userRepository.update(user)).thenReturn(user);

        User result = userService.changePassword("john.doe", "new");

        assertEquals(user, result);

    }

    @Test
    void changeActivityStatus() throws GymException {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(userRepository.update(user)).thenReturn(user);

        User result = userService.changeActivityStatus("john.doe");

        assertEquals(user, result);
    }

//    @Test
//    void testChangePassword_Success() throws GymException {
//        // Given
//        User user = new User();
//        String newPassword = "newPassword";
//
//        when(userRepository.update(user)).thenReturn(user);
//
//        // When
//        User result = userService.changePassword("john.doe", newPassword);
//
//        // Then
//        assertEquals(newPassword, result.getPassword());
//        verify(userRepository, times(1)).update(user);
//    }
//
//    @Test
//    void testChangeActivityStatus_Success() throws GymException {
//        // Given
//        User user = new User();
//        user.setIsActive(true); // Current status is active
//
//        when(userRepository.update(user)).thenReturn(user);
//
//        // When
//        User result = userService.changeActivityStatus("john.doe");
//
//        // Then
//        assertFalse(result.getIsActive()); // Status should be toggled to inactive
//        verify(userRepository, times(1)).update(user);
//    }
//
//    @Test
//    void testChangeActivityStatus_InactiveToActive() throws GymException {
//        // Given
//        User user = new User();
//        user.setIsActive(false); // Current status is inactive
//
//        when(userRepository.update(user)).thenReturn(user);
//
//        // When
//        User result = userService.changeActivityStatus("john.doe");
//
//        // Then
//        assertTrue(result.getIsActive()); // Status should be toggled to active
//        verify(userRepository, times(1)).update(user);
//    }
}
