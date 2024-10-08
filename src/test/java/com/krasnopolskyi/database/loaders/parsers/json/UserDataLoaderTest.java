package com.krasnopolskyi.database.loaders.parsers.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDataLoaderTest {

    @Mock
    private Storage storage;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserDataLoader userDataLoader;

    private static final String USERS_PATH = "src/test/data/load/users.json"; // Set the path to the users.json file

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        // Directly set the usersPath
        userDataLoader = new UserDataLoader(storage, objectMapper);
        setUsersPath(USERS_PATH);
    }

    private void setUsersPath(String path) throws Exception {
        Field field = UserDataLoader.class.getDeclaredField("usersPath");
        field.setAccessible(true); // Allow access to the private field
        field.set(userDataLoader, path);
        System.out.println("USERS PATH " + USERS_PATH);
    }

    @Test
    void testLoadData_fileNotFound() throws Exception {
        // Given
        setUsersPath("invalid/path/to/users.json");

        // Execute
        userDataLoader.loadData();

        // Then
        verify(storage, never()).getUsers(); // Should not interact with storage if file is not found
    }

    @Test
    void testLoadData_ioException() throws IOException {
        // Given
        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenThrow(new IOException("IO error"));

        // Execute
        userDataLoader.loadData();

        // Then
        verify(storage, never()).getUsers(); // Should not interact with storage if there's an IOException
    }

//    @Test
//    void testLoadDataSuccessfully() throws Exception {
//        // Given
//        List<User> users = Arrays.asList(
//                new User(1L, "John", "Doe", "john.doe", "pass", true),
//                new User(2L, "Jane", "Doe", "jane.doe", "pass", true)
//        );
//
//        when(objectMapper.readValue(any(InputStream.class), eq(new TypeReference<List<User>>() {}))).thenReturn(users);
//
//        // Assuming the storage has a method to hold users
//        when(storage.getUsers()).thenReturn(new HashMap<>());
//
//        // When
//        userDataLoader.loadData();
//
//        // Then
//        verify(objectMapper, times(1)).readValue(any(InputStream.class), any(TypeReference.class));
//        verify(storage.getUsers(), times(1)).put(1L, users.get(0));
//        verify(storage.getUsers(), times(1)).put(2L, users.get(1));
//        assertEquals(2, storage.getUsers().size());
//    }
}
