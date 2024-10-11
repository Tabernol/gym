package com.krasnopolskyi.utils;

import com.krasnopolskyi.database.dao.UserRepository;
import com.krasnopolskyi.exception.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsernameGeneratorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsernameGenerator usernameGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGenerateUsername_noConflicts() throws ValidateException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "john.doe";

        // When
        when(userRepository.isUsernameExist("john.doe")).thenReturn(false);

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist("john.doe");
    }

    @Test
    void testGenerateUsername_withConflicts() throws ValidateException {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "john.doe2";

        // When
        when(userRepository.isUsernameExist("john.doe")).thenReturn(true); // First username exists
        when(userRepository.isUsernameExist("john.doe1")).thenReturn(true); // First variant exists
        when(userRepository.isUsernameExist("john.doe2")).thenReturn(false); // Second variant does not exist

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist("john.doe");
        verify(userRepository).isUsernameExist("john.doe1");
        verify(userRepository).isUsernameExist("john.doe2");
    }

    @Test
    void testGenerateUsername_withMultipleConflicts() throws ValidateException {
        // Given
        String firstName = "Jane";
        String lastName = "Smith";
        String expectedUsername = "jane.smith4";

        // When
        // Mock multiple existing usernames
        when(userRepository.isUsernameExist("jane.smith")).thenReturn(true);
        when(userRepository.isUsernameExist("jane.smith1")).thenReturn(true);
        when(userRepository.isUsernameExist("jane.smith2")).thenReturn(true);
        when(userRepository.isUsernameExist("jane.smith3")).thenReturn(true);
        when(userRepository.isUsernameExist("jane.smith4")).thenReturn(false); // Available username

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist("jane.smith");
        verify(userRepository).isUsernameExist("jane.smith1");
        verify(userRepository).isUsernameExist("jane.smith2");
        verify(userRepository).isUsernameExist("jane.smith3");
        verify(userRepository).isUsernameExist("jane.smith4");
    }

    @Test
    void testGenerateUsername_emptyNames() {
        // Given
        String firstName = "";
        String lastName = "";

        // When & Then
        ValidateException thrown = assertThrows(ValidateException.class, () -> {
            usernameGenerator.generateUsername(firstName, lastName);
        });

        // Assert the expected exception message
        assertEquals("First name or Last name must consist of at least two letters", thrown.getMessage());
    }
}
