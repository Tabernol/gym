package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
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
import java.util.ArrayList;
import java.util.List;

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

    private TraineeDto traineeDto;
    private TraineeResponseDto expectedResponse;

    TrainingResponseDto expectedTraining;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        traineeDto = TraineeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        expectedResponse = new TraineeResponseDto(
                "John",
                "Doe",
                "john.doe",
                LocalDate.of(1990, 1, 1),
                "123 Main St"
        );

        expectedTraining = new TrainingResponseDto(1L, "first Cardio", "Cardio",
                "Arni Schwartz", "John gold",
                LocalDate.of(2024,1,1), 1000);

    }

    @Test
    void testCreateTrainee() throws GymException {
        // Mock the behavior of traineeService.save
        when(traineeService.save(traineeDto)).thenReturn(expectedResponse);

        // Call the method
        TraineeResponseDto result = mainFacade.createTrainee(traineeDto);

        // Verify that the service method was called
        verify(traineeService).save(traineeDto);

        // Assert the expected result
        assertEquals(expectedResponse, result);
    }

    @Test
    void testFindTraineeById_success() throws EntityException {
        Long id = 1L;

        // Mock the behavior of traineeService.findById
        when(traineeService.findById(id)).thenReturn(expectedResponse);

        // Call the method
        TraineeResponseDto result = mainFacade.findTraineeById(id);

        // Verify that the service method was called
        verify(traineeService).findById(id);

        // Assert the expected result
        assertEquals(expectedResponse, result);
    }

    @Test
    void testUpdateTrainee() throws GymException {
        // Mock the behavior of traineeService.update
        when(traineeService.update(traineeDto)).thenReturn(expectedResponse);

        // Call the method
        TraineeResponseDto result = mainFacade.updateTrainee(traineeDto);

        // Verify that the service method was called
        verify(traineeService).update(traineeDto);

        // Assert the expected result
        assertEquals(expectedResponse, result);
    }

//    @Test
//    void testDeleteTrainee() throws EntityException {
//        // Mock the behavior of traineeService.delete
//        when(traineeService.delete(traineeDto)).thenReturn(true);
//
//        // Call the method
//        boolean result = mainFacade.deleteTrainee(traineeDto);
//
//        // Verify that the service method was called
//        verify(traineeService).delete(traineeDto);
//
//        // Assert the expected result
//        assertTrue(result);
//    }

    @Test
    void testCreateTrainer_success() throws GymException {
        TrainerDto trainerDto = TrainerDto.builder().firstName("John").lastName("Doe").specialization(15).build();
        TrainerResponseDto expectedResponse = new TrainerResponseDto("John", "Doe", "john.doe", "1");

        // Mock the behavior of trainerService.save
        when(trainerService.save(trainerDto)).thenReturn(expectedResponse);

        // Call the method
        TrainerResponseDto result = mainFacade.createTrainer(trainerDto);

        // Verify that the service method was called
        verify(trainerService).save(trainerDto);

        // Assert the expected result
        assertEquals(expectedResponse, result);
    }

    @Test
    void testCreateTrainer_validateException() throws GymException {
        TrainerDto trainerDto = TrainerDto.builder().firstName("John").lastName("Doe").specialization(15).build();

        // Mock the behavior of trainerService.save to throw ValidateException
        when(trainerService.save(trainerDto)).thenThrow(new ValidateException("Invalid specialization"));

        // Call the method
        TrainerResponseDto result = mainFacade.createTrainer(trainerDto);

        // Verify that the service method was called
        verify(trainerService).save(trainerDto);

        // Assert that the result is null (because of the exception)
        assertNull(result);
    }

    @Test
    void testFindTrainerById_success() throws EntityException {
        Long id = 1L;
        TrainerResponseDto expectedTrainer = new TrainerResponseDto("John", "Gold", "john.gold", "Cardio");

        // Mock the behavior of trainerService.findById
        when(trainerService.findById(id)).thenReturn(expectedTrainer);

        // Call the method
        TrainerResponseDto result = mainFacade.findTrainerById(id);

        // Verify that the service method was called
        verify(trainerService).findById(id);

        // Assert the expected result
        assertEquals(expectedTrainer, result);
    }

    @Test
    void testAddTraining_success() throws ValidateException {
        TrainingDto trainingDto = TrainingDto.builder()
                .trainingName("Cardio")
                .trainingType(1)
                .date(LocalDate.of(2024, 9, 3))
                .duration(1000)
                .traineeId(1L)
                .trainerId(1L)
                .build();


        // Mock the behavior of trainingService.save
        when(trainingService.save(trainingDto)).thenReturn(expectedTraining);

        // Call the method
        TrainingResponseDto result = mainFacade.addTraining(trainingDto);

        // Verify that the service method was called
        verify(trainingService).save(trainingDto);

        // Assert the expected result
        assertEquals(expectedTraining, result);
    }

    @Test
    void testAddTraining_validateException() throws ValidateException {
        TrainingDto trainingDto = TrainingDto.builder()
                .trainingName("Cardio")
                .trainingType(1)
                .date(LocalDate.of(2024, 9, 3))
                .duration(1000)
                .traineeId(1L)
                .trainerId(1L)
                .build();

        // Mock the behavior of trainingService.save to throw ValidateException
        when(trainingService.save(trainingDto)).thenThrow(new ValidateException("Invalid data"));

        // Call the method
        TrainingResponseDto result = mainFacade.addTraining(trainingDto);

        // Verify that the service method was called
        verify(trainingService).save(trainingDto);

        // Assert that the result is null (because of the exception)
        assertNull(result);
    }

    @Test
    void testFindTrainingById_success() throws EntityException {
        Long id = 1L;

        // Mock the behavior of trainingService.findById
        when(trainingService.findById(id)).thenReturn(expectedTraining);

        // Call the method
        TrainingResponseDto result = mainFacade.findTrainingById(id);

        // Verify that the service method was called
        verify(trainingService).findById(id);

        // Assert the expected result
        assertEquals(expectedTraining, result);
    }

    @Test
    void updateTrainer() throws GymException {
        TrainerDto trainerDto = TrainerDto.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(1)
                .build();

        TrainerResponseDto expectedResponse = new TrainerResponseDto("John", "Doe", "john.doe", "1");

        // Mock the behavior of trainerService.update (not save)
        when(trainerService.update(trainerDto)).thenReturn(expectedResponse);

        // Call the method
        TrainerResponseDto result = mainFacade.updateTrainer(trainerDto);

        // Verify that the service method update was called (not save)
        verify(trainerService).update(trainerDto);

        // Assert the expected result
        assertEquals(expectedResponse, result);
    }

    @Test
    void testCreateTrainee_validationFailure() throws GymException {
        // Given
        TraineeDto traineeDto = TraineeDto.builder().firstName("Jo").lastName("l").build(); // Create a valid TraineeDto instance

        // When
        when(traineeService.save(traineeDto)).thenThrow(new ValidateException("Invalid trainee data"));

        // Execute
        TraineeResponseDto actualResponse = mainFacade.createTrainee(traineeDto);

        // Then
        assertNull(actualResponse);
        verify(traineeService).save(traineeDto);
        // Optionally verify that the warning was logged
        // Use a logging framework test utility to capture logs if needed
    }

    @Test
    void getAllTrainingsByUsernameAndFilter_ShouldReturnTrainings() throws ValidateException {
        // Arrange
        TrainingFilterDto filterDto = TrainingFilterDto.builder().build();; // Define the filter criteria here
        List<TrainingResponseDto> expectedTrainings = List.of(expectedTraining);

        when(trainingService.getFilteredTrainings(filterDto)).thenReturn(expectedTrainings);

        // Act
        List<TrainingResponseDto> result = mainFacade.getAllTrainingsByUsernameAndFilter(filterDto);

        // Assert
        assertEquals(expectedTrainings, result);
        verify(trainingService, times(1)).getFilteredTrainings(filterDto);
    }

    @Test
    void getAllTrainingsByUsernameAndFilter_ShouldHandleException() throws ValidateException {
        // Arrange
        TrainingFilterDto filterDto = TrainingFilterDto.builder().build();
        when(trainingService.getFilteredTrainings(filterDto)).thenThrow(new ValidateException("Validation failed"));

        // Act
        List<TrainingResponseDto> result = mainFacade.getAllTrainingsByUsernameAndFilter(filterDto);

        // Assert
        assertTrue(result.isEmpty());
        verify(trainingService, times(1)).getFilteredTrainings(filterDto);
    }

    @Test
    void getAllNotAssignedTrainersByTraineeUsername_ShouldReturnTrainers() throws EntityException {
        // Arrange
        String username = "john.doe";
        List<TrainerResponseDto> expectedTrainers = new ArrayList<>();
        when(traineeService.findAllNotAssignedTrainersByTrainee(username)).thenReturn(expectedTrainers);

        // Act
        List<TrainerResponseDto> result = mainFacade.getAllNotAssignedTrainersByTraineeUsername(username);

        // Assert
        assertEquals(expectedTrainers, result);
        verify(traineeService, times(1)).findAllNotAssignedTrainersByTrainee(username);
    }

    @Test
    void getAllNotAssignedTrainersByTraineeUsername_ShouldHandleException() throws EntityException {
        // Arrange
        String username = "john.doe";
        when(traineeService.findAllNotAssignedTrainersByTrainee(username)).thenThrow(new EntityException("Entity not found"));

        // Act
        List<TrainerResponseDto> result = mainFacade.getAllNotAssignedTrainersByTraineeUsername(username);

        // Assert
        assertTrue(result.isEmpty());
        verify(traineeService, times(1)).findAllNotAssignedTrainersByTrainee(username);
    }

    @Test
    void updateTrainers_ShouldReturnUpdatedTrainers() throws EntityException {
        // Arrange
        List<TrainerDto> newTrainers = new ArrayList<>();
        List<TrainerResponseDto> updatedTrainers = new ArrayList<>();
        when(traineeService.updateTrainers(traineeDto, newTrainers)).thenReturn(updatedTrainers);

        // Act
        List<TrainerResponseDto> result = mainFacade.updateTrainers(traineeDto, newTrainers);

        // Assert
        assertEquals(updatedTrainers, result);
        verify(traineeService, times(1)).updateTrainers(traineeDto, newTrainers);
    }

    @Test
    void updateTrainers_ShouldHandleException() throws EntityException {
        // Arrange
        List<TrainerDto> newTrainers = new ArrayList<>();
        when(traineeService.updateTrainers(traineeDto, newTrainers)).thenThrow(new EntityException("Update failed"));

        // Act
        List<TrainerResponseDto> result = mainFacade.updateTrainers(traineeDto, newTrainers);

        // Assert
        assertTrue(result.isEmpty());
        verify(traineeService, times(1)).updateTrainers(traineeDto, newTrainers);
    }

    @Test
    void deleteTrainee_ShouldReturnTrueWhenDeleted() throws EntityException {
        // Arrange
        String username = "john.doe";
        when(traineeService.delete(username)).thenReturn(true);

        // Act
        boolean result = mainFacade.deleteTrainee(username);

        // Assert
        assertTrue(result);
        verify(traineeService, times(1)).delete(username);
    }

    @Test
    void deleteTrainee_ShouldReturnFalseWhenException() throws EntityException {
        // Arrange
        String username = "john.doe";
        when(traineeService.delete(username)).thenThrow(new EntityException("Delete failed"));

        // Act
        boolean result = mainFacade.deleteTrainee(username);

        // Assert
        assertFalse(result);
        verify(traineeService, times(1)).delete(username);
    }

    @Test
    void findTraineeByUsername_ShouldReturnTrainee() throws EntityException {
        // Arrange
        String username = "john.doe";
        when(traineeService.findByUsername(username)).thenReturn(expectedResponse);

        // Act
        TraineeResponseDto result = mainFacade.findTraineeByUsername(username);

        // Assert
        assertEquals(expectedResponse, result);
        verify(traineeService, times(1)).findByUsername(username);
    }

    @Test
    void findTraineeByUsername_ShouldHandleException() throws EntityException {
        // Arrange
        String username = "john.doe";
        when(traineeService.findByUsername(username)).thenThrow(new EntityException("Find failed"));

        // Act
        TraineeResponseDto result = mainFacade.findTraineeByUsername(username);

        // Assert
        assertNull(result);
        verify(traineeService, times(1)).findByUsername(username);
    }
}
