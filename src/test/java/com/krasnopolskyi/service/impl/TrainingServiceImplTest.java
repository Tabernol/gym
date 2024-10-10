//package com.krasnopolskyi.service.impl;
//
//import com.krasnopolskyi.database.dao.impl.TraineeRepository;
//import com.krasnopolskyi.database.dao.TrainerRepository;
//import com.krasnopolskyi.database.dao.TrainingRepository;
//import com.krasnopolskyi.database.dao.TrainingTypeRepository;
//import com.krasnopolskyi.dto.request.TrainingDto;
//import com.krasnopolskyi.entity.Trainee;
//import com.krasnopolskyi.entity.Trainer;
//import com.krasnopolskyi.entity.Training;
//import com.krasnopolskyi.entity.TrainingType;
//import com.krasnopolskyi.exception.EntityNotFoundException;
//import com.krasnopolskyi.exception.ValidateException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class TrainingServiceImplTest {
//
//    @Mock
//    private TrainingRepository trainingRepository;
//
//    @Mock
//    private TraineeRepository traineeRepository;
//
//    @Mock
//    private TrainerRepository trainerRepository;
//
//    @Mock
//    private TrainingTypeRepository trainingTypeRepository;
//
//    @InjectMocks
//    private TrainingServiceImpl trainingService;
//
//    private TrainingDto trainingDto;
//    private Trainee trainee;
//    private Trainer trainer;
//    private TrainingType trainingType;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Set up a sample Training object
//        trainingDto = TrainingDto.builder()
//                .trainingName("Cardio")
//                .trainingType(1)
//                .date(LocalDate.of(2024, 9,3))
//                .duration(1000)
//                .traineeId(1L)
//                .trainerId(1L)
//                .build();
//
//        // Set up corresponding entities
//        trainee = Trainee.builder().id(1L).build();
//
//        trainer = Trainer.builder().id(1L).specialization(1).userId(101L).build();
//
//        trainingType = new TrainingType(1, "Cardio");
//    }
//
//    @Test
//    public void testSave_Success() throws ValidateException {
//        // Mock repositories to return the corresponding entities
//        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
//        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
//        when(trainingTypeRepository.findById(1)).thenReturn(Optional.of(trainingType));
//
//        // Mock trainingRepository to save the training and return it
//        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
//            // Return the training object with the generated ID
//            Training savedTraining = invocation.getArgument(0);
//            savedTraining.builder().id(1L).build(); // Simulate ID generation
//            return savedTraining;
//        });
//
//        Training savedTraining = trainingService.save(trainingDto);
//
//        // Assert that the saved training object is not null and has an ID
//        assertNotNull(savedTraining);
//        assertNotNull(savedTraining.getId());
//        assertEquals(trainingDto.getDate(), savedTraining.getDate());
//        assertEquals(trainingDto.getDuration(), savedTraining.getDuration());
//        assertEquals(trainingDto.getTrainingName(), savedTraining.getTrainingName());
//
//        // Verify that the necessary repository methods were called
//        verify(traineeRepository, times(1)).findById(1L);
//        verify(trainerRepository, times(1)).findById(1L);
//        verify(trainingTypeRepository, times(1)).findById(1);
//        verify(trainingRepository, times(1)).save(any(Training.class));
//    }
//
//    @Test
//    public void testSave_TraineeNotFound() {
//        // Mock trainee repository to return empty
//        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Assert that an IllegalArgumentException is thrown
//        ValidateException thrown = assertThrows(ValidateException.class, () -> {
//            trainingService.save(trainingDto);
//        });
//
//        assertEquals("Could not find trainee with id 1", thrown.getMessage());
//
//        // Verify that the other repository methods were not called
//        verify(trainerRepository, never()).findById(1L);
//        verify(trainingTypeRepository, never()).findById(1);
//        verify(trainingRepository, never()).save(any(Training.class));
//    }
//
//    @Test
//    public void testSave_TrainerNotFound() {
//        // Mock trainee repository to return the trainee
//        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
//        // Mock trainer repository to return empty
//        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Assert that an IllegalArgumentException is thrown
//        ValidateException thrown = assertThrows(ValidateException.class, () -> {
//            trainingService.save(trainingDto);
//        });
//
//        assertEquals("Could not find trainer with id 1", thrown.getMessage());
//
//        // Verify that the trainingTypeRepository and trainingRepository were not called
//        verify(trainingTypeRepository, never()).findById(1);
//        verify(trainingRepository, never()).save(any(Training.class));
//    }
//
//    @Test
//    public void testSave_TrainingTypeNotFound() {
//        // Mock trainee repository to return the trainee
//        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
//        // Mock trainer repository to return the trainer
//        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
//        // Mock training type repository to return empty
//        when(trainingTypeRepository.findById(1)).thenReturn(Optional.empty());
//
//        // Assert that an IllegalArgumentException is thrown
//        ValidateException thrown = assertThrows(ValidateException.class, () -> {
//            trainingService.save(trainingDto);
//        });
//
//        assertEquals("Could not find training type with id 1", thrown.getMessage());
//
//        // Verify that the trainingRepository was not called
//        verify(trainingRepository, never()).save(any(Training.class));
//    }
//
//    @Test
//    public void testFindById_Success() throws EntityNotFoundException {
//        Training training = Training.builder()
//                .id(1L)
//                .trainingName("Cardio")
//                .trainingType(1)
//                .date(LocalDate.of(2024, 9,3))
//                .duration(1000)
//                .traineeId(1L)
//                .trainerId(1L)
//                .build();
//
//
//        // Mock the training repository to return the training object
//        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
//
//        Training result = trainingService.findById(1L);
//
//        // Assert that the training object is retrieved correctly
//        assertEquals(training, result);
//
//        // Verify that findById() was called once with the correct ID
//        verify(trainingRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void testFindById_NotFound() {
//        // Mock the training repository to return empty when the training is not found
//        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Assert that an EntityNotFoundException is thrown
//        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
//            trainingService.findById(1L);
//        });
//
//        assertEquals("Could not found training with id 1", thrown.getMessage());
//
//        // Verify that findById() was called once with the correct ID
//        verify(trainingRepository, times(1)).findById(1L);
//    }
//}
