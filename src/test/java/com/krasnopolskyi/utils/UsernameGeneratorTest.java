package com.krasnopolskyi.utils;

import com.krasnopolskyi.database.dao.UserRepository;
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
    void testGenerateUsername_noConflicts() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "John.Doe";

        // When
        when(userRepository.isUsernameExist("John.Doe")).thenReturn(false);

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist("John.Doe");
    }

    @Test
    void testGenerateUsername_withConflicts() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "John.Doe2";

        // When
        when(userRepository.isUsernameExist("John.Doe")).thenReturn(true); // First username exists
        when(userRepository.isUsernameExist("John.Doe1")).thenReturn(true); // First variant exists
        when(userRepository.isUsernameExist("John.Doe2")).thenReturn(false); // Second variant does not exist

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist("John.Doe");
        verify(userRepository).isUsernameExist("John.Doe1");
        verify(userRepository).isUsernameExist("John.Doe2");
    }

    @Test
    void testGenerateUsername_withMultipleConflicts() {
        // Given
        String firstName = "Jane";
        String lastName = "Smith";
        String expectedUsername = "Jane.Smith4";

        // When
        // Mock multiple existing usernames
        when(userRepository.isUsernameExist("Jane.Smith")).thenReturn(true);
        when(userRepository.isUsernameExist("Jane.Smith1")).thenReturn(true);
        when(userRepository.isUsernameExist("Jane.Smith2")).thenReturn(true);
        when(userRepository.isUsernameExist("Jane.Smith3")).thenReturn(true);
        when(userRepository.isUsernameExist("Jane.Smith4")).thenReturn(false); // Available username

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist("Jane.Smith");
        verify(userRepository).isUsernameExist("Jane.Smith1");
        verify(userRepository).isUsernameExist("Jane.Smith2");
        verify(userRepository).isUsernameExist("Jane.Smith3");
        verify(userRepository).isUsernameExist("Jane.Smith4");
    }

    @Test
    void testGenerateUsername_emptyNames() {
        // Given
        String firstName = "";
        String lastName = "";
        String expectedUsername = ".";

        // When
        when(userRepository.isUsernameExist(".")).thenReturn(false);

        // Execute
        String actualUsername = usernameGenerator.generateUsername(firstName, lastName);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepository).isUsernameExist(".");
    }
}
