package com.krasnopolskyi.database.loaders;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.database.StorageUtils;
import com.krasnopolskyi.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class DataLoaderPostConstructTest {

    @Mock
    private Storage storage;

    @Mock
    private StorageUtils storageUtils;

    @Mock
    private Map<Long, User> usersMap;

    @Mock
    private Map<Long, Trainee> traineesMap;

    @Mock
    private Map<Long, Trainer> trainersMap;

    @Mock
    private Map<Long, Training> trainingsMap;

    @Mock
    private Map<Integer, TrainingType> trainingTypesMap;

    @InjectMocks
    private DataLoaderPostConstruct dataLoaderPostConstruct;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Manually set the @Value fields using reflection
        setField(dataLoaderPostConstruct, "usersPath", "path/to/users.json");
        setField(dataLoaderPostConstruct, "traineesPath", "path/to/trainees.json");
        setField(dataLoaderPostConstruct, "trainersPath", "path/to/trainers.json");
        setField(dataLoaderPostConstruct, "trainingsPath", "path/to/trainings.json");
        setField(dataLoaderPostConstruct, "trainingTypesPath", "path/to/trainingTypes.json");

        // Mock the Storage maps to return mock maps instead of real HashMap instances
        when(storage.getUsers()).thenReturn(usersMap);
        when(storage.getTrainees()).thenReturn(traineesMap);
        when(storage.getTrainers()).thenReturn(trainersMap);
        when(storage.getTrainings()).thenReturn(trainingsMap);
        when(storage.getTrainingTypes()).thenReturn(trainingTypesMap);
    }

    // Helper method to set private fields via reflection
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testLoadDataFromJson_successfulLoads() throws IOException {
        // Mock successful data loading
        when(storageUtils.loadFromJsonFile(anyString(), eq(Long.class), eq(User.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(anyString(), eq(Long.class), eq(Trainee.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(anyString(), eq(Long.class), eq(Trainer.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(anyString(), eq(Long.class), eq(Training.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(anyString(), eq(Integer.class), eq(TrainingType.class))).thenReturn(mock(Map.class));

        // Call the method
        dataLoaderPostConstruct.loadDataFromJson();

        // Verify interactions with storage and storageUtils
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/users.json", Long.class, User.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainees.json", Long.class, Trainee.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainers.json", Long.class, Trainer.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainings.json", Long.class, Training.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainingTypes.json", Integer.class, TrainingType.class);

        // Verify the maps are updated
        verify(usersMap, times(1)).putAll(anyMap());
        verify(traineesMap, times(1)).putAll(anyMap());
        verify(trainersMap, times(1)).putAll(anyMap());
        verify(trainingsMap, times(1)).putAll(anyMap());
        verify(trainingTypesMap, times(1)).putAll(anyMap());
    }

    @Test
    void testLoadDataFromJson_partialFailure() throws IOException {
        // Mock successful and failed data loading
        when(storageUtils.loadFromJsonFile(eq("path/to/users.json"), eq(Long.class), eq(User.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(eq("path/to/trainees.json"), eq(Long.class), eq(Trainee.class))).thenThrow(new IOException("Failed to load trainees"));
        when(storageUtils.loadFromJsonFile(eq("path/to/trainers.json"), eq(Long.class), eq(Trainer.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(eq("path/to/trainings.json"), eq(Long.class), eq(Training.class))).thenReturn(mock(Map.class));
        when(storageUtils.loadFromJsonFile(eq("path/to/trainingTypes.json"), eq(Integer.class), eq(TrainingType.class))).thenReturn(mock(Map.class));

        // Call the method
        dataLoaderPostConstruct.loadDataFromJson();

        // Verify that data loading proceeded despite the failure
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/users.json", Long.class, User.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainees.json", Long.class, Trainee.class); // Failed
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainers.json", Long.class, Trainer.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainings.json", Long.class, Training.class);
        verify(storageUtils, times(1)).loadFromJsonFile("path/to/trainingTypes.json", Integer.class, TrainingType.class);

        verify(usersMap, times(1)).putAll(anyMap());
        verify(traineesMap, times(0)).putAll(anyMap()); // Trainees failed
        verify(trainersMap, times(1)).putAll(anyMap());
        verify(trainingsMap, times(1)).putAll(anyMap());
        verify(trainingTypesMap, times(1)).putAll(anyMap());
    }
}
