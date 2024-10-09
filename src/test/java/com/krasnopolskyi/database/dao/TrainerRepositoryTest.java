package com.krasnopolskyi.database.dao;

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

    @InjectMocks
    private TrainerRepository trainerRepository;

    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


    }

//    @Test
//    public void testSave() {
//        // Mock the map to return null on first put, then return the saved trainer on second put
//        when(trainersMap.put(trainer.getId(), trainer)).thenReturn(null).thenReturn(trainer);
//
//        Trainer savedTrainer = trainerRepository.save(trainer);
//
//        // Verify that the trainer was saved twice in the map
//        verify(trainersMap, times(2)).put(trainer.getId(), trainer);
//
//        // Assert that the saved trainer is the one we saved in the second call
//        assertEquals(trainer, savedTrainer);
//    }
//
//    @Test
//    public void testFindById() {
//        // Mock the map to return the trainer when queried by ID using getOrDefault
//        when(trainersMap.getOrDefault(trainer.getId(), null)).thenReturn(trainer);
//
//        Optional<Trainer> result = trainerRepository.findById(trainer.getId());
//
//        // Verify that the trainer is retrieved correctly
//        assertTrue(result.isPresent());
//        assertEquals(trainer, result.get());
//    }
}
