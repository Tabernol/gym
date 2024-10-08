package com.krasnopolskyi.database;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageUtilsTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private StorageUtils storageUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveToFile_success() throws Exception {
        String fileName = "data/test-users.json";
        Map<String, String> data = Map.of("key", "value");

        // Mock file path creation
        Path filePath = Path.of(fileName);
        Files.createDirectories(filePath.getParent()); // Ensure directory exists

        // Test saving the file
        storageUtils.saveToFile(fileName, data);

        // Verify objectMapper.writeValue() was called correctly
        verify(objectMapper).writeValue(any(BufferedWriter.class), eq(data));
    }

    @Test
    void testSaveToFile_exception() throws Exception {
        String fileName = "data/invalid-path/test-users.json";
        Map<String, String> data = Map.of("key", "value");

        // Simulate a failure when creating directories or writing to the file
        doThrow(IOException.class).when(objectMapper).writeValue(any(BufferedWriter.class), eq(data));

        // Test the exception handling in saveToFile
        storageUtils.saveToFile(fileName, data);

        // Ensure that the exception was logged, but not thrown
        verify(objectMapper).writeValue(any(BufferedWriter.class), eq(data));
        // The log should contain the error message but the test should not fail.
    }
}
