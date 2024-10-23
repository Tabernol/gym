package com.krasnopolskyi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class TrainingServiceTest {
    @InjectMocks
    private TrainingService trainingService;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private UserRepository userRepository;

    private TrainingDto trainingDto;
    private TrainingResponseDto trainingDtoExpected;
    private Training training =  new Training();
    private Trainee trainee;
    private Trainer trainer;
    private User user1 = new User();
    private User user2 = new User();
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingType = new TrainingType(1, "Cardio");
        trainingDto = TrainingDto.builder()
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("test")
                .trainingType(1)
                .date(LocalDate.of(2024,1,1))
                .duration(60)
                .build();

        user1.setId(1L);
        user1.setUsername("john.doe");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("123");

        user2.setId(1L);
        user2.setUsername("usain.bolt");
        user2.setFirstName("Usain");
        user2.setLastName("Bolt");
        user2.setPassword("123");


        trainingDtoExpected = new TrainingResponseDto(1l, "test", "Cardio", "Usain Bolt", "John Doe", LocalDate.of(2024,1,1), 60);

        trainee = new Trainee(); // Assuming you have a default constructor
        trainee.setId(1L);
        trainee.setUser(user1);


        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user2);
        trainer.setSpecialization(trainingType); // Set specialization to match training type


        training.setId(1L);
        training.setTrainingName("test");
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainingType);
        training.setDate(LocalDate.of(2024,1,1));
        training.setDuration(60);
    }

    @Test
    void testSaveTraineeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect exception
        assertThrows(EntityException.class, () -> trainingService.save(trainingDto));
    }

    @Test
    void testSaveTrainingTypeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findById(1)).thenReturn(Optional.empty());

        // Call the service method and expect exception
        assertThrows(EntityException.class, () -> trainingService.save(trainingDto));
    }

    @Test
    void testFindByIdNotFound() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityException.class, () -> trainingService.findById(1L));
    }

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
        EntityException thrown = assertThrows(EntityException.class, () -> trainingService.save(trainingDto));
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
        EntityException thrown = assertThrows(EntityException.class, () -> trainingService.save(trainingDto));
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
        EntityException thrown = assertThrows(EntityException.class, () -> trainingService.save(trainingDto));
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

    @Test
    void findById() throws EntityException {
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(training));

        TrainingResponseDto result = trainingService.findById(1L);

//        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    public void getFilteredTrainingsThrow() {

        assertThrows(EntityException.class,() -> trainingService.getFilteredTrainings(TrainingFilterDto.builder().owner("test").build()));
    }

    @Test
    public void getFilteredTrainingsSuccess() throws EntityException {
        TrainingFilterDto test = TrainingFilterDto.builder().owner("test").build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user1));
        when(trainingRepository.getFilteredTrainings(any(TrainingFilterDto.class))).thenReturn(List.of(training));

        List<TrainingResponseDto> result = trainingService.getFilteredTrainings(test);

        assertEquals(List.of(trainingDtoExpected), result);
    }

    @Test
    void saveTrainingTest() throws ValidateException, EntityException {

        when(traineeRepository.findById(trainingDto.getTraineeId())).thenReturn(Optional.ofNullable(trainee));
        when(trainerRepository.findById(trainingDto.getTrainerId())).thenReturn(Optional.ofNullable(trainer));
        when(trainingTypeRepository.findById(trainingDto.getTrainingType())).thenReturn(Optional.ofNullable(trainingType));
        when(trainingRepository.save(any(Training.class))).thenReturn(training);


        TrainingResponseDto result = trainingService.save(trainingDto);

        assertEquals(trainingDtoExpected.trainingName(), result.trainingName());
        assertEquals(trainingDtoExpected.trainingType(), result.trainingType());
        assertEquals(trainingDtoExpected.traineeFullName(), result.traineeFullName());
        assertEquals(trainingDtoExpected.trainerFullName(), result.trainerFullName());
        assertTrue(trainee.getTrainers().contains(trainer));
        assertTrue(trainer.getTrainees().contains(trainee));
    }
}
