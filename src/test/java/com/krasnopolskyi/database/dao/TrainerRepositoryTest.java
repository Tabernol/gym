package com.krasnopolskyi.database.dao;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerRepositoryTest {

    @Mock
    private Storage storage;

    @Mock
    private Map<Long, Trainer> trainersMap;

    @InjectMocks
    private TrainerRepository trainerRepository;

    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = Trainer.builder().id(1L).userId(101L).specialization(1).build();

        // Mock the behavior of storage.getTrainers() to return the trainersMap
        when(storage.getTrainers()).thenReturn(trainersMap);
    }

    @Test
    public void testSave() {
        // Mock the map to return null on first put, then return the saved trainer on second put
        when(trainersMap.get(trainer.getId())).thenReturn(trainer);

        Trainer savedTrainer = trainerRepository.save(trainer);

        // Verify that the trainer was saved twice in the map
        verify(trainersMap, times(1)).put(trainer.getId(), trainer);

        // Assert that the saved trainer is the one we saved in the second call
        assertEquals(trainer, savedTrainer);
        assertEquals(trainer.getSpecialization(), savedTrainer.getSpecialization());
        assertEquals(trainer.getUserId(), savedTrainer.getUserId());
    }

    @Test
    public void testFindById() {
        // Mock the map to return the trainer when queried by ID using getOrDefault
        when(trainersMap.getOrDefault(trainer.getId(), null)).thenReturn(trainer);

        Optional<Trainer> result = trainerRepository.findById(trainer.getId());

        // Verify that the trainer is retrieved correctly
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }
}
