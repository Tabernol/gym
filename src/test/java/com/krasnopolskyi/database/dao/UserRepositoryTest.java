package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).login("testUser").build();

    }

//    @Test
//    public void testSave() {
//        // Simulate saving the user twice into the map (as per the method behavior)
//        when(usersMap.put(user.getId(), user)).thenReturn(null).thenReturn(user);
//
//        User savedUser = userRepository.save(user);
//
//        // Assert that the user is saved correctly after the second put
//        assertEquals(user, savedUser);
//
//        // Verify that the map's put method was called twice
//        verify(usersMap, times(2)).put(user.getId(), user);
//    }
//
//    @Test
//    public void testFindById_Success() {
//        // Mock the map to return the user when queried by ID
//        when(usersMap.getOrDefault(user.getId(), null)).thenReturn(user);
//
//        Optional<User> result = userRepository.findById(user.getId());
//
//        // Assert that the user is retrieved correctly
//        assertTrue(result.isPresent());
//        assertEquals(user, result.get());
//
//        // Verify that getOrDefault was called once with the correct ID
//        verify(usersMap, times(1)).getOrDefault(user.getId(), null);
//    }
//
//    @Test
//    public void testFindById_NotFound() {
//        // Mock the map to return null when the user is not found
//        when(usersMap.getOrDefault(user.getId(), null)).thenReturn(null);
//
//        Optional<User> result = userRepository.findById(user.getId());
//
//        // Assert that no user was found
//        assertFalse(result.isPresent());
//
//        // Verify that getOrDefault was called once with the correct ID
//        verify(usersMap, times(1)).getOrDefault(user.getId(), null);
//    }
//
//    @Test
//    public void testUpdate() {
//        // Simulate updating the user in the map
//        when(usersMap.put(user.getId(), user)).thenReturn(user);
//
//        User updatedUser = userRepository.update(user);
//
//        // Assert that the user is updated correctly
//        assertEquals(user, updatedUser);
//
//        // Verify that the map's put method was called once with the correct ID and user
//        verify(usersMap, times(1)).put(user.getId(), user);
//    }
//
//    @Test
//    public void testDelete_Success() {
//        // Simulate the map's remove method returning true
//        when(usersMap.remove(user.getId(), user)).thenReturn(true);
//
//        boolean result = userRepository.delete(user);
//
//        // Assert that the user is deleted successfully
//        assertTrue(result);
//
//        // Verify that remove was called once with the correct ID and user
//        verify(usersMap, times(1)).remove(user.getId(), user);
//    }
//
//    @Test
//    public void testDelete_Failure() {
//        // Simulate the map's remove method returning false
//        when(usersMap.remove(user.getId(), user)).thenReturn(false);
//
//        boolean result = userRepository.delete(user);
//
//        // Assert that the delete operation failed
//        assertFalse(result);
//
//        // Verify that remove was called once with the correct ID and user
//        verify(usersMap, times(1)).remove(user.getId(), user);
//    }
//
//    @Test
//    public void testIsUsernameExist_True() {
//        // Mock the map to contain a user with the matching username
//        when(usersMap.values()).thenReturn(List.of(user));
//
//        boolean result = userRepository.isUsernameExist(user.getLogin());
//
//        // Assert that the username exists
//        assertTrue(result);
//
//        // Verify that values was called to check for usernames
//        verify(usersMap, times(1)).values();
//    }
//
//    @Test
//    public void testIsUsernameExist_False() {
//        // Mock the map to contain users without the matching username
//        when(usersMap.values()).thenReturn(List.of(user));
//
//        boolean result = userRepository.isUsernameExist("nonExistingUser");
//
//        // Assert that the username does not exist
//        assertFalse(result);
//
//        // Verify that values was called to check for usernames
//        verify(usersMap, times(1)).values();
//    }
}
