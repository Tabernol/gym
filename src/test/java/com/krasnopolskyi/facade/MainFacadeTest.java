package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private MainFacade mainFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee() {
        TraineeDto traineeDto = TraineeDto.builder().build();
        UserCredentials expectedCredentials = new UserCredentials("user", "password");

        // Mock the behavior of traineeService.save
        when(traineeService.save(traineeDto)).thenReturn(expectedCredentials);

        // Call the method
        UserCredentials result = mainFacade.createTrainee(traineeDto);

        // Verify that the service method was called
        verify(traineeService).save(traineeDto);

        // Assert the expected result
        assertEquals(expectedCredentials, result);
    }

    @Test
    void testFindTraineeById_success() throws EntityNotFoundException {
        Long id = 1L;
        Trainee expectedTrainee = Trainee.builder().id(id).build();

        // Mock the behavior of traineeService.findById
        when(traineeService.findById(id)).thenReturn(expectedTrainee);

        // Call the method
        Trainee result = mainFacade.findTraineeById(id);

        // Verify that the service method was called
        verify(traineeService).findById(id);

        // Assert the expected result
        assertEquals(expectedTrainee, result);
    }

    @Test
    void testFindTraineeById_notFound() throws EntityNotFoundException {
        Long id = 1L;

        // Mock the behavior of traineeService.findById to throw EntityNotFoundException
        when(traineeService.findById(id)).thenThrow(new EntityNotFoundException("not found"));

        // Call the method and expect an exception
        assertThrows(EntityNotFoundException.class, () -> mainFacade.findTraineeById(id));

        // Verify that the service method was called
        verify(traineeService).findById(id);
    }

    @Test
    void testUpdateTrainee() {
        Trainee trainee = new Trainee();

        // Mock the behavior of traineeService.update
        when(traineeService.update(trainee)).thenReturn(trainee);

        // Call the method
        Trainee result = mainFacade.updateTrainee(trainee);

        // Verify that the service method was called
        verify(traineeService).update(trainee);

        // Assert the expected result
        assertEquals(trainee, result);
    }

    @Test
    void testDeleteTrainee() {
        Trainee trainee = new Trainee();

        // Mock the behavior of traineeService.delete
        when(traineeService.delete(trainee)).thenReturn(true);

        // Call the method
        boolean result = mainFacade.deleteTrainee(trainee);

        // Verify that the service method was called
        verify(traineeService).delete(trainee);

        // Assert the expected result
        assertTrue(result);
    }

    @Test
    void testCreateTrainer_success() throws ValidateException {
        TrainerDto trainerDto = new TrainerDto("john", "black", 1);
        UserCredentials expectedCredentials = new UserCredentials("john.black", "password");

        // Mock the behavior of trainerService.save
        when(trainerService.save(trainerDto)).thenReturn(expectedCredentials);

        // Call the method
        UserCredentials result = mainFacade.createTrainer(trainerDto);

        // Verify that the service method was called
        verify(trainerService).save(trainerDto);

        // Assert the expected result
        assertEquals(expectedCredentials, result);
    }

    @Test
    void testUpdateTrainer_success() throws ValidateException {
        Trainer trainer = new Trainer(1L, 101L, 1);

        // Mock the behavior of trainerService.save
        when(trainerService.update(any(Trainer.class))).thenReturn(trainer);

        // Call the method
        Trainer result = mainFacade.updateTrainer(trainer);

        // Verify that the service method was called
        verify(trainerService).update(trainer);

        // Assert the expected result
        assertEquals(trainer.getId(), result.getId());
        assertEquals(trainer.getSpecialization(), result.getSpecialization());
        assertEquals(trainer.getUserId(), result.getUserId());
    }

    @Test
    void testCreateTrainer_validateException() throws ValidateException {
        TrainerDto trainerDto = new TrainerDto("john", "black", 15);

        // Mock the behavior of trainerService.save to throw ValidateException
        when(trainerService.save(trainerDto)).thenThrow(new ValidateException("Invalid specialization"));

        // Call the method
        UserCredentials result = mainFacade.createTrainer(trainerDto);

        // Verify that the service method was called
        verify(trainerService).save(trainerDto);

        // Assert that the result contains empty username and password
        assertEquals("", result.getUsername());
        assertEquals("", result.getPassword());
    }

    @Test
    void testFindTrainerById_success() throws EntityNotFoundException {
        Long id = 1L;
        Trainer expectedTrainer = Trainer.builder().id(id).build();

        // Mock the behavior of trainerService.findById
        when(trainerService.findById(id)).thenReturn(expectedTrainer);

        // Call the method
        Trainer result = mainFacade.findTrainerById(id);

        // Verify that the service method was called
        verify(trainerService).findById(id);

        // Assert the expected result
        assertEquals(expectedTrainer, result);
    }

    @Test
    void testAddTraining() throws ValidateException {
        TrainingDto trainingDto = TrainingDto.builder()
                .trainingName("Cardio2")
                .trainingType(1)
                .date(LocalDate.of(2024, 9,3))
                .duration(1000)
                .traineeId(1L)
                .trainerId(1L)
                .build();

        Training training = Training.builder()
                .trainingName("Cardio2")
                .trainingType(1)
                .date(LocalDate.of(2024, 9,3))
                .duration(1000)
                .traineeId(1L)
                .trainerId(1L)
                .build();

        // Mock the behavior of trainingService.save
        when(trainingService.save(trainingDto)).thenReturn(training);

        // Call the method
        Training result = mainFacade.addTraining(trainingDto);

        // Verify that the service method was called
        verify(trainingService).save(trainingDto);

        // Assert the expected result
        assertEquals(training, result);
    }

    @Test
    void testAddTraining_ValidateException() throws ValidateException {
        TrainingDto trainingDto = TrainingDto.builder()
                .trainingName("Cardio2")
                .trainingType(1)
                .date(LocalDate.of(2024, 9,3))
                .duration(1000)
                .traineeId(1L)
                .trainerId(1L)
                .build();

        // Mock the behavior of trainingService.save
        when(trainingService.save(trainingDto)).thenThrow(new ValidateException("test"));

        // Call the method
        Training result = mainFacade.addTraining(trainingDto);

        // Verify that the service method was called
        verify(trainingService).save(trainingDto);

        assertThrows(ValidateException.class, () -> trainingService.save(trainingDto));
    }

    @Test
    void testFindTrainingById_success() throws EntityNotFoundException {
        Long id = 1L;
        Training expectedTraining = Training.builder()
                .id(1L)
                .trainingName("Cardio2")
                .trainingType(1)
                .date(LocalDate.of(2024, 9,3))
                .duration(1000)
                .traineeId(1L)
                .trainerId(1L)
                .build();

        // Mock the behavior of trainingService.findById
        when(trainingService.findById(id)).thenReturn(expectedTraining);

        // Call the method
        Training result = mainFacade.findTrainingById(id);

        // Verify that the service method was called
        verify(trainingService).findById(id);

        // Assert the expected result
        assertEquals(expectedTraining, result);
    }
}
