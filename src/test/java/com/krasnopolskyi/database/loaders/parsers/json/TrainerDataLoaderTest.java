package com.krasnopolskyi.database.loaders.parsers.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerDataLoaderTest {

    @Mock
    private Storage storage;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private InputStream inputStream;

    @InjectMocks
    private TrainerDataLoader trainerDataLoader;

    private static final String TRAINERS_PATH = "src/resources/data/load/trainees.json";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        // Directly set the trainersPath
        trainerDataLoader = new TrainerDataLoader(storage, objectMapper);
        setTrainersPath("src/test/data/load/trainers.json");
    }

    private void setTrainersPath(String path) throws Exception {
        var field = TrainerDataLoader.class.getDeclaredField("trainersPath");
        field.setAccessible(true); // Allow access to the private field
        field.set(trainerDataLoader, path);
        System.out.println("TRAINERS PATH " + TRAINERS_PATH);
    }

    @Test
    void testLoadData_fileNotFound() throws Exception {
        // Given
        setTrainersPath("invalid/path/to/trainers.json");

        // Execute
        trainerDataLoader.loadData();

        // Then
        verify(storage, never()).getTrainers(); // Should not interact with storage if file is not found
    }

    @Test
    void testLoadData_ioException() throws IOException {
        // Given
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read()).thenThrow(new IOException("IO error"));
        when(objectMapper.readValue(inputStream, new TypeReference<List<Trainer>>() {})).thenThrow(new IOException());

        // Execute
        trainerDataLoader.loadData();

        // Then
        verify(storage, never()).getTrainers(); // Should not interact with storage if there's an IOException
    }

//    @Test
//    void testInsertData() {
//        // Given
//        Trainer trainer1 = Trainer.builder().id(1L).build();
//        Trainer trainer2 = Trainer.builder().id(2L).build();
//        List<Trainer> trainers = List.of(trainer1, trainer2);
//
//        when(storage.getTrainers()).thenReturn(mock(Map.class)); // Mocking the trainer map
//
//        // Execute
//        trainerDataLoader.insertData(trainers); // Call the method directly
//
//        // Then
//        verify(storage.getTrainers()).put(trainer1.getId(), trainer1);
//        verify(storage.getTrainers()).put(trainer2.getId(), trainer2);
//    }
}
