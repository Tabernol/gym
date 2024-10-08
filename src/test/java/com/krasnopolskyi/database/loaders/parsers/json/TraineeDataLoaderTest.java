package com.krasnopolskyi.database.loaders.parsers.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeDataLoaderTest {

    @Mock
    private Storage storage;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TraineeDataLoader traineeDataLoader;

    private static final String TRAINEES_PATH = "path/to/trainees.json"; // Update this to match your configuration

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        // Use reflection to set the traineesPath field
        setTraineesPath(TRAINEES_PATH);
    }

    private void setTraineesPath(String path) throws Exception {
        var field = TraineeDataLoader.class.getDeclaredField("traineesPath");
        field.setAccessible(true); // Allow access to the private field
        field.set(traineeDataLoader, path);
    }

    @Test
    public void testLoadData_Success() throws IOException {
        // Prepare mock data
        List<Trainee> trainees = new ArrayList<>();
        Trainee trainee1 = Trainee.builder().id(1L).build();
        Trainee trainee2 = Trainee.builder().id(2L).build();
        trainees.add(trainee1);
        trainees.add(trainee2);

        // Mock the ObjectMapper to simulate reading from a JSON file
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TRAINEES_PATH);
        when(objectMapper.readValue(inputStream, new TypeReference<List<Trainee>>() {})).thenReturn(trainees);

        // Mock the storage to simulate storing the trainees
        when(storage.getTrainees()).thenReturn(new HashMap<>());

        // Call the method to test
        traineeDataLoader.loadData();

        // Verify that insertData() is called and that the trainees are inserted
        verify(storage, times(1)).getTrainees();
        for (Trainee trainee : trainees) {
            verify(storage.getTrainees(), times(1)).put(trainee.getId(), trainee);
        }
    }

    @Test
    public void testLoadData_FileNotFound() throws Exception {
        // Simulate the scenario where the input stream is null (file not found)
        setTraineesPath("invalid/path/to/trainees.json");

        // Call the method to test
        traineeDataLoader.loadData();

        // Verify that no insertion occurs
        verify(storage, never()).getTrainees();
    }

    @Test
    public void testLoadData_IOError() throws IOException {
        // Prepare a scenario that throws an IOException when reading the input stream
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read()).thenThrow(new IOException("IO error"));

        // Mock the ObjectMapper to throw an IOException
        when(objectMapper.readValue(inputStream, new TypeReference<List<Trainee>>() {})).thenThrow(new IOException("IO error"));

        // Mock the storage
        when(storage.getTrainees()).thenReturn(new HashMap<>());

        // Call the method to test
        traineeDataLoader.loadData();

        // Verify that no insertion occurs
        verify(storage, never()).getTrainees();
    }
}
