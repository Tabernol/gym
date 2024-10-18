package com.krasnopolskyi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.repository.TrainerRepository;
import com.krasnopolskyi.repository.TrainingRepository;
import com.krasnopolskyi.repository.TrainingTypeRepository;
import com.krasnopolskyi.utils.mapper.TrainingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TrainingServiceImplTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    private TrainingDto trainingDto;
    private Trainee trainee;
    private Trainer trainer;
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        trainingDto = TrainingDto.builder()
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Test Training")
                .trainingType(1)
                .date(LocalDate.now())
                .duration(60)
                .build();

        trainee = new Trainee(); // Assuming you have a default constructor
        trainee.setId(1L);
        trainer = new Trainer();
        trainer.setId(1L);
        trainingType = new TrainingType();
        trainingType.setId(1); // Set an ID for training type
        trainer.setSpecialization(trainingType); // Set specialization to match training type
    }

    @Test
    void testSaveTraineeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect exception
        assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
    }

    @Test
    void testSaveTrainingTypeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findById(1)).thenReturn(Optional.empty());

        // Call the service method and expect exception
        assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
    }

    @Test
    void testFindByIdNotFound() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityException.class, () -> trainingService.findById(1L));
    }

    @Test
    void testGetFilteredTrainings_Success() throws ValidateException {
        // Given
        TrainingFilterDto filter = new TrainingFilterDto(); // Setup your filter as needed

        // Mocking the Training objects and their properties
        Training training1 = mock(Training.class);
        Training training2 = mock(Training.class);

        // Mocking Trainer and Trainee objects
        User trainerUser1 = mock(User.class);
        User traineeUser1 = mock(User.class);
        User trainerUser2 = mock(User.class);
        User traineeUser2 = mock(User.class);

        Trainer trainer1 = mock(Trainer.class);
        Trainee trainee1 = mock(Trainee.class);
        Trainer trainer2 = mock(Trainer.class);
        Trainee trainee2 = mock(Trainee.class);

        // Setting up the mocks for Training 1
        when(trainerUser1.getFirstName()).thenReturn("John");
        when(trainerUser1.getLastName()).thenReturn("Doe");
        when(trainer1.getUser()).thenReturn(trainerUser1);
        when(training1.getTrainer()).thenReturn(trainer1);

        when(traineeUser1.getFirstName()).thenReturn("Jane");
        when(traineeUser1.getLastName()).thenReturn("Smith");
        when(trainee1.getUser()).thenReturn(traineeUser1);
        when(training1.getTrainee()).thenReturn(trainee1);

        when(training1.getId()).thenReturn(1L);
        when(training1.getTrainingName()).thenReturn("Yoga Class");
        when(training1.getTrainingType()).thenReturn(mock(TrainingType.class));
        when(training1.getTrainingType().getType()).thenReturn("Yoga");
        when(training1.getDate()).thenReturn(LocalDate.of(2024, 10, 17));
        when(training1.getDuration()).thenReturn(60);

        // Setting up the mocks for Training 2
        when(trainerUser2.getFirstName()).thenReturn("Alice");
        when(trainerUser2.getLastName()).thenReturn("Johnson");
        when(trainer2.getUser()).thenReturn(trainerUser2);
        when(training2.getTrainer()).thenReturn(trainer2);

        when(traineeUser2.getFirstName()).thenReturn("Bob");
        when(traineeUser2.getLastName()).thenReturn("Brown");
        when(trainee2.getUser()).thenReturn(traineeUser2);
        when(training2.getTrainee()).thenReturn(trainee2);

        when(training2.getId()).thenReturn(2L);
        when(training2.getTrainingName()).thenReturn("Pilates Class");
        when(training2.getTrainingType()).thenReturn(mock(TrainingType.class));
        when(training2.getTrainingType().getType()).thenReturn("Pilates");
        when(training2.getDate()).thenReturn(LocalDate.of(2024, 10, 18));
        when(training2.getDuration()).thenReturn(45);

        List<Training> mockTrainings = new ArrayList<>();
        mockTrainings.add(training1);
        mockTrainings.add(training2);

        // Mock the repository call
        when(trainingRepository.getFilteredTrainings(filter)).thenReturn(mockTrainings);

        // When
        List<TrainingResponseDto> result = trainingService.getFilteredTrainings(filter);

        // Then
        assertEquals(2, result.size());

        // Validate the first TrainingResponseDto
        TrainingResponseDto responseDto1 = result.get(0);
        assertEquals(1L, responseDto1.id());
        assertEquals("Yoga Class", responseDto1.trainingName());
        assertEquals("Yoga", responseDto1.trainingType());
        assertEquals("John Doe", responseDto1.trainerFullName());
        assertEquals("Jane Smith", responseDto1.traineeFullName());
        assertEquals(LocalDate.of(2024, 10, 17), responseDto1.date());
        assertEquals(60, responseDto1.duration());

        // Validate the second TrainingResponseDto
        TrainingResponseDto responseDto2 = result.get(1);
        assertEquals(2L, responseDto2.id());
        assertEquals("Pilates Class", responseDto2.trainingName());
        assertEquals("Pilates", responseDto2.trainingType());
        assertEquals("Alice Johnson", responseDto2.trainerFullName());
        assertEquals("Bob Brown", responseDto2.traineeFullName());
        assertEquals(LocalDate.of(2024, 10, 18), responseDto2.date());
        assertEquals(45, responseDto2.duration());

        verify(trainingRepository, times(1)).getFilteredTrainings(filter); // Verify that repository method was called
    }

//    @Test
//    void testSaveTraining_Success() throws ValidateException {
//        // Given
//        TrainingDto trainingDto = new TrainingDto();
//        trainingDto.setTraineeId(1L);
//        trainingDto.setTrainerId(2L);
//        trainingDto.setTrainingName("Yoga Class");
//        trainingDto.setTrainingType(3); // Assume this is the ID of the TrainingType
//        trainingDto.setDate(LocalDate.of(2024, 10, 17));
//        trainingDto.setDuration(60);
//
//        TrainingResponseDto responseDto = new TrainingResponseDto(
//                1L,
//                "Yoga Class",
//                "Yoga",
//                "trainer",
//                "trainee",
//                LocalDate.of(2024, 10, 17),
//                60);
//
//
//        Trainee trainee = mock(Trainee.class);
//        Trainer trainer = mock(Trainer.class);
//        TrainingType trainingType = mock(TrainingType.class);
//        Training training = new Training();
//
//        when(traineeRepository.findById(trainingDto.getTraineeId())).thenReturn(Optional.of(trainee));
//        when(trainerRepository.findById(trainingDto.getTrainerId())).thenReturn(Optional.of(trainer));
//        when(trainingTypeRepository.findById(trainingDto.getTrainingType())).thenReturn(Optional.of(trainingType));
//
//        when(trainer.getSpecialization()).thenReturn(mock(TrainingType.class));
//        when(trainingType.getId()).thenReturn(3); // Matching with the trainingType ID
//        when(trainer.getSpecialization().getId()).thenReturn(3);
////        when(training.getId()).thenReturn(1L); // Mocking ID for training entity
//
//        // Setting expectations for training object to be saved
////        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
////            Training savedTraining = invocation.getArgument(0);
////            savedTraining.setId(1L); // Setting ID on saved training
////            return savedTraining;
////        });
//        when(TrainingMapper.mapToDto(any(Training.class))).thenReturn(responseDto);
//
//        // When
//        TrainingResponseDto result = trainingService.save(trainingDto);
//
//        // Then
//        assertEquals("Yoga Class", result.trainingName());
//        assertEquals(LocalDate.of(2024, 10, 17), result.date());
//        assertEquals(60, result.duration());
//        verify(trainingRepository, times(1)).save(any(Training.class)); // Verify save method was called
//        verify(trainee.getTrainers(), times(1)).add(trainer); // Verify trainee-trainer relationship
//        verify(trainer.getTrainees(), times(1)).add(trainee); // Verify trainer-trainee relationship
//    }

    @Test
    void testSaveTraining_TraineeNotFound() {
        // Given
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTraineeId(1L);
        trainingDto.setTrainerId(2L);
        trainingDto.setTrainingName("Yoga Class");
        trainingDto.setTrainingType(3);
        trainingDto.setDate(LocalDate.of(2024, 10, 17));
        trainingDto.setDuration(60);

        when(traineeRepository.findById(trainingDto.getTraineeId())).thenReturn(Optional.empty());

        // When & Then
        ValidateException thrown = assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
        assertEquals("Could not find trainee with id 1", thrown.getMessage());
    }

    @Test
    void testSaveTraining_TrainerNotFound() {
        // Given
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTraineeId(1L);
        trainingDto.setTrainerId(2L);
        trainingDto.setTrainingName("Yoga Class");
        trainingDto.setTrainingType(3);
        trainingDto.setDate(LocalDate.of(2024, 10, 17));
        trainingDto.setDuration(60);

        Trainee trainee = mock(Trainee.class);
        when(traineeRepository.findById(trainingDto.getTraineeId())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findById(trainingDto.getTrainerId())).thenReturn(Optional.empty());

        // When & Then
        ValidateException thrown = assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
        assertEquals("Could not find trainer with id 2", thrown.getMessage());
    }

    @Test
    void testSaveTraining_TrainingTypeNotFound() {
        // Given
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTraineeId(1L);
        trainingDto.setTrainerId(2L);
        trainingDto.setTrainingName("Yoga Class");
        trainingDto.setTrainingType(3);
        trainingDto.setDate(LocalDate.of(2024, 10, 17));
        trainingDto.setDuration(60);

        Trainee trainee = mock(Trainee.class);
        Trainer trainer = mock(Trainer.class);

        when(traineeRepository.findById(trainingDto.getTraineeId())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findById(trainingDto.getTrainerId())).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findById(trainingDto.getTrainingType())).thenReturn(Optional.empty());

        // When & Then
        ValidateException thrown = assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
        assertEquals("Could not find training type with id 3", thrown.getMessage());
    }

    @Test
    void testSaveTraining_InvalidTrainerSpecialization() {
        // Given
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTraineeId(1L);
        trainingDto.setTrainerId(2L);
        trainingDto.setTrainingName("Yoga Class");
        trainingDto.setTrainingType(3);
        trainingDto.setDate(LocalDate.of(2024, 10, 17));
        trainingDto.setDuration(60);

        Trainee trainee = mock(Trainee.class);
        Trainer trainer = mock(Trainer.class);
        TrainingType trainingType = mock(TrainingType.class);

        when(traineeRepository.findById(trainingDto.getTraineeId())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findById(trainingDto.getTrainerId())).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findById(trainingDto.getTrainingType())).thenReturn(Optional.of(trainingType));

        when(trainer.getSpecialization()).thenReturn(mock(TrainingType.class));
        when(trainer.getSpecialization().getId()).thenReturn(4); // Not matching with the trainingType ID

        // When & Then
        ValidateException thrown = assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
        assertEquals("This trainer is not assigned to this training type", thrown.getMessage());
    }
}
