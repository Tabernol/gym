package com.krasnopolskyi.utils;

import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsernameGeneratorTest {

    @Mock
    private UserRepositoryImpl userRepositoryImpl;

    @InjectMocks
    private UsernameGenerator usernameGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGenerateUsername_noConflicts() {
        // Given
        String expectedUsername = "John.Doe";
        UserDto userDto = new UserDto("John", "Doe");

        // When
        when(userRepositoryImpl.isUsernameExist("John.Doe")).thenReturn(false);

        // Execute
        String actualUsername = usernameGenerator.generateUsername(userDto);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepositoryImpl).isUsernameExist("John.Doe");
    }

    @Test
    void testGenerateUsername_withConflicts() {
        // Given
        UserDto userDto = new UserDto("John", "Doe");
        String expectedUsername = "John.Doe2";

        // When
        when(userRepositoryImpl.isUsernameExist("John.Doe")).thenReturn(true); // First username exists
        when(userRepositoryImpl.isUsernameExist("John.Doe1")).thenReturn(true); // First variant exists
        when(userRepositoryImpl.isUsernameExist("John.Doe2")).thenReturn(false); // Second variant does not exist

        // Execute
        String actualUsername = usernameGenerator.generateUsername(userDto);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepositoryImpl).isUsernameExist("John.Doe");
        verify(userRepositoryImpl).isUsernameExist("John.Doe1");
        verify(userRepositoryImpl).isUsernameExist("John.Doe2");
    }

    @Test
    void testGenerateUsername_withMultipleConflicts() {
        // Given
        UserDto userDto = new UserDto("John", "Doe");
        String expectedUsername = "Jane.Smith4";

        // When
        // Mock multiple existing usernames
        when(userRepositoryImpl.isUsernameExist("Jane.Smith")).thenReturn(true);
        when(userRepositoryImpl.isUsernameExist("Jane.Smith1")).thenReturn(true);
        when(userRepositoryImpl.isUsernameExist("Jane.Smith2")).thenReturn(true);
        when(userRepositoryImpl.isUsernameExist("Jane.Smith3")).thenReturn(true);
        when(userRepositoryImpl.isUsernameExist("Jane.Smith4")).thenReturn(false); // Available username

        // Execute
        String actualUsername = usernameGenerator.generateUsername(userDto);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepositoryImpl).isUsernameExist("Jane.Smith");
        verify(userRepositoryImpl).isUsernameExist("Jane.Smith1");
        verify(userRepositoryImpl).isUsernameExist("Jane.Smith2");
        verify(userRepositoryImpl).isUsernameExist("Jane.Smith3");
        verify(userRepositoryImpl).isUsernameExist("Jane.Smith4");
    }

    @Test
    void testGenerateUsername_emptyNames() {
        // Given
        UserDto userDto = new UserDto("", "");
        String expectedUsername = ".";

        // When
        when(userRepositoryImpl.isUsernameExist(".")).thenReturn(false);

        // Execute
        String actualUsername = usernameGenerator.generateUsername(userDto);

        // Then
        assertEquals(expectedUsername, actualUsername);
        verify(userRepositoryImpl).isUsernameExist(".");
    }
}
