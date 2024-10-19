package com.krasnopolskyi.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.krasnopolskyi.dto.request.UserCredentials;
import com.krasnopolskyi.exception.AccessException;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthenticationManagerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_SuccessfulAuthentication() throws EntityException {
        // Arrange
        UserCredentials credentials = new UserCredentials("user", "password");
        when(userService.checkCredentials(credentials)).thenReturn(true);

        // Act
        String result = authenticationManager.login(credentials);

        // Assert
        assertEquals("Authentication successful", result);
    }

    @Test
    void testLogin_FailedAuthentication() throws EntityException {
        // Arrange
        UserCredentials credentials = new UserCredentials("user", "wrongPassword");
        when(userService.checkCredentials(credentials)).thenReturn(false);

        // Act
        String result = authenticationManager.login(credentials);

        // Assert
        assertEquals("Authentication failed", result);
    }

    @Test
    void testLogin_UserNotFound() throws EntityException {
        // Arrange
        UserCredentials credentials = new UserCredentials("unknownUser", "password");
        when(userService.checkCredentials(credentials)).thenThrow(new EntityException("User not found"));

        // Act
        String result = authenticationManager.login(credentials);

        // Assert
        assertEquals("Authentication failed", result);
    }

    @Test
    void testCheckPermissions_UserHasAccess() throws EntityException {
        // Arrange
        String username = "user";
        UserCredentials credentials = new UserCredentials(username, "password");
        when(userService.checkCredentials(credentials)).thenReturn(true);
        authenticationManager.login(credentials); // Successful login

        // Act & Assert
        assertDoesNotThrow(() -> authenticationManager.checkPermissions(username));
    }

    @Test
    void testCheckPermissions_UserNoAccess() {
        // Arrange
        String username = "unauthorizedUser";

        // Act & Assert
        AccessException exception = assertThrows(AccessException.class, () -> {
            authenticationManager.checkPermissions(username);
        });
        assertEquals("Please check your credentials and try again", exception.getMessage());
    }
}
