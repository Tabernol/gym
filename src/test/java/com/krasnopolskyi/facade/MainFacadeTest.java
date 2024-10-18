package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.AccessException;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.security.AuthenticationManager;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingService;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.validation.CommonValidator;
import com.krasnopolskyi.validation.group.Create;
import jakarta.validation.ConstraintViolationException;
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
    @Mock
    private AuthenticationManager manager;
    @Mock
    private UserService userService;
    @Mock
    private CommonValidator validator;

    @InjectMocks
    private MainFacade mainFacade;
    private TraineeDto traineeDto;
    private TraineeResponseDto expectedTrainee;
    private TrainingResponseDto expectedTraining;
    private TrainerDto trainerDto;
    private TrainerResponseDto expectedTrainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainerDto = TrainerDto.builder()
                .firstName("Arni")
                .specialization(1)
                .id(1L)
                .lastName("schwarz")
                .build();
        expectedTrainer = new TrainerResponseDto("Arni", "schwarz", "arni.schwarz", "Cardio");


        traineeDto = TraineeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        expectedTrainee = new TraineeResponseDto(
                "John",
                "Doe",
                "john.doe",
                LocalDate.of(1990, 1, 1),
                "123 Main St"
        );

        expectedTraining = new TrainingResponseDto(1L, "first Cardio", "Cardio",
                "Arni Schwartz", "John Doe",
                LocalDate.of(2024, 1, 1), 1000);

    }

    @Test
    void testChangePasswordFailure() throws GymException {
        String executor = "testExecutor";
        String password = "newPassword";

        // Mocking behavior to throw AccessException
        doThrow(new AccessException("Permission denied")).when(manager).checkPermissions(executor);

        // Executing the method
        boolean result = mainFacade.changePassword(password, executor);

        // Verifying results
        assertFalse(result);
        verify(manager).checkPermissions(executor);
        verify(userService, never()).findByUsername(executor);
        verify(userService, never()).changePassword(any(), anyString());
    }

    @Test
    void testCreateTraineeSuccess() throws GymException {
        // Mocking behavior
        doNothing().when(validator).validate(traineeDto, Create.class);
        when(traineeService.save(traineeDto)).thenReturn(expectedTrainee);

        // Executing the method
        TraineeResponseDto result = mainFacade.createTrainee(traineeDto);

        // Verifying results
        assertNotNull(result);
        assertEquals(expectedTrainee, result);
        verify(validator).validate(traineeDto, Create.class);
        verify(traineeService).save(traineeDto);
    }

    @Test
    void testCreateTraineeValidationFailure() throws GymException {

        // Mocking behavior to throw ConstraintViolationException
        doThrow(new ConstraintViolationException(null)).when(validator).validate(traineeDto, Create.class);

        // Executing the method
        TraineeResponseDto result = mainFacade.createTrainee(traineeDto);

        // Verifying results
        assertNull(result);
        verify(validator).validate(traineeDto, Create.class);
        verify(traineeService, never()).save(any(TraineeDto.class));
    }

    @Test
    void testFindTraineeByIdSuccess() throws EntityException, AccessException {
        Long traineeId = 1L;
        String executor = "testExecutor";
        // Mocking behavior
        doNothing().when(manager).checkPermissions(executor);
        when(traineeService.findById(traineeId)).thenReturn(expectedTrainee);

        // Executing the method
        TraineeResponseDto result = mainFacade.findTraineeById(traineeId, executor);

        // Verifying results
        assertNotNull(result);
        assertEquals(expectedTrainee, result);
        verify(manager).checkPermissions(executor);
        verify(traineeService).findById(traineeId);
    }

    @Test
    void testFindTraineeByIdAccessFailure() throws AccessException, EntityException {
        Long traineeId = 1L;
        String executor = "testExecutor";

        // Mocking behavior to throw AccessException
        doThrow(new AccessException("Permission denied")).when(manager).checkPermissions(executor);

        // Executing the method
        TraineeResponseDto result = mainFacade.findTraineeById(traineeId, executor);

        // Verifying results
        assertNull(result);
        verify(manager).checkPermissions(executor);
        verify(traineeService, never()).findById(traineeId);
    }

    @Test
    void shouldNotChangePasswordDueToAccessException() throws GymException {
        String newPassword = "newPassword123";
        String executor = "executor";

        doThrow(new AccessException("No permission")).when(manager).checkPermissions(executor);

        boolean result = mainFacade.changePassword(newPassword, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(userService, never()).findByUsername(anyString());
        verify(userService, never()).changePassword(any(User.class), anyString());
        assertFalse(result);
    }

    @Test
    void shouldCreateTraineeSuccessfully() throws GymException {
        when(traineeService.save(any(TraineeDto.class))).thenReturn(expectedTrainee);

        TraineeResponseDto result = mainFacade.createTrainee(traineeDto);

        verify(validator, times(1)).validate(traineeDto, Create.class);
        verify(traineeService, times(1)).save(traineeDto);
        assertEquals(expectedTrainee, result);
    }

    @Test
    void shouldNotCreateTraineeDueToValidationFailure() throws GymException {
        doThrow(new ConstraintViolationException(null)).when(validator).validate(any(), any());

        TraineeResponseDto result = mainFacade.createTrainee(traineeDto);

        verify(validator, times(1)).validate(traineeDto, Create.class);
        verify(traineeService, never()).save(any());
        assertNull(result);
    }

    @Test
    void shouldFindTraineeByIdSuccessfully() throws AccessException, EntityException {
        String executor = "executor";
        when(traineeService.findById(anyLong())).thenReturn(expectedTrainee);

        TraineeResponseDto result = mainFacade.findTraineeById(1L, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, times(1)).findById(1L);
        assertEquals(expectedTrainee, result);
    }

    @Test
    void shouldFailToFindTraineeByIdDueToAccessException() throws EntityException, AccessException {
        String executor = "executor";
        doThrow(new AccessException("No permission")).when(manager).checkPermissions(executor);

        TraineeResponseDto result = mainFacade.findTraineeById(1L, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, never()).findById(anyLong());
        assertNull(result);
    }

    @Test
    void shouldAddTrainingSuccessfully() throws AccessException, ValidateException {
        String executor = "executor";
        when(trainingService.save(any(TrainingDto.class))).thenReturn(expectedTraining);

        TrainingDto trainingDto = new TrainingDto();
        TrainingResponseDto result = mainFacade.addTraining(trainingDto, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(validator, times(1)).validate(trainingDto, Create.class);
        verify(trainingService, times(1)).save(trainingDto);
        assertEquals(expectedTraining, result);
    }

    @Test
    void shouldNotAddTrainingDueToValidationFailure() throws ValidateException, AccessException {
        String executor = "executor";
        TrainingDto trainingDto = new TrainingDto();
        doThrow(new ConstraintViolationException(null)).when(validator).validate(trainingDto, Create.class);

        TrainingResponseDto result = mainFacade.addTraining(trainingDto, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(validator, times(1)).validate(trainingDto, Create.class);
        verify(trainingService, never()).save(any(TrainingDto.class));
        assertNull(result);
    }

    // Test for findTraineeByUsername

    @Test
    void shouldNotFindTraineeDueToEntityException() throws AccessException, EntityException {
        String username = "john.doe";
        String executor = "executor";

        doThrow(new EntityException("Trainee not found")).when(traineeService).findByUsername(username);

        TraineeResponseDto result = mainFacade.findTraineeByUsername(username, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, times(1)).findByUsername(username);
        assertNull(result);
    }

    @Test
    void shouldNotFindTraineeDueToAccessException() throws EntityException, AccessException {
        String username = "john.doe";
        String executor = "executor";

        doThrow(new AccessException("No permission")).when(manager).checkPermissions(executor);

        TraineeResponseDto result = mainFacade.findTraineeByUsername(username, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, never()).findByUsername(username);
        assertNull(result);
    }

    // Test for updateTrainee

    @Test
    void shouldNotUpdateTraineeDueToConstraintViolation() throws GymException {
        doThrow(new ConstraintViolationException(null)).when(validator).validate(any(), any());

        TraineeResponseDto result = mainFacade.updateTrainee(traineeDto, "executor");

        verify(manager, times(1)).checkPermissions("executor");
        verify(validator, times(1)).validate(traineeDto, Create.class);
        verify(traineeService, never()).update(any());
        assertNull(result);
    }

    // Test findTraineeByUsername
    @Test
    void shouldFindTraineeByUsernameSuccessfully() throws EntityException, AccessException {
        String username = "john.doe";
        String executor = "executor";

        when(traineeService.findByUsername(username)).thenReturn(expectedTrainee);

        TraineeResponseDto result = mainFacade.findTraineeByUsername(username, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, times(1)).findByUsername(username);
        assertEquals(expectedTrainee, result);
    }

    @Test
    void shouldReturnNullWhenFindTraineeByUsernameThrowsEntityException() throws EntityException, AccessException {
        String username = "john.doe";
        String executor = "executor";

        when(traineeService.findByUsername(username)).thenThrow(new EntityException("Trainee not found"));

        TraineeResponseDto result = mainFacade.findTraineeByUsername(username, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, times(1)).findByUsername(username);
        assertNull(result);
    }

    // Test updateTrainee
    @Test
    void shouldUpdateTraineeSuccessfully() throws GymException {
        when(traineeService.update(any(TraineeDto.class))).thenReturn(expectedTrainee);

        TraineeResponseDto result = mainFacade.updateTrainee(traineeDto, "executor");

        verify(manager, times(1)).checkPermissions("executor");
        verify(validator, times(1)).validate(traineeDto, Create.class);
        verify(traineeService, times(1)).update(traineeDto);
        assertEquals(expectedTrainee, result);
    }

    @Test
    void shouldReturnNullWhenUpdateTraineeThrowsConstraintViolationException() throws GymException {
        doThrow(new ConstraintViolationException(null)).when(validator).validate(any(), any());

        TraineeResponseDto result = mainFacade.updateTrainee(traineeDto, "executor");

        verify(validator, times(1)).validate(traineeDto, Create.class);
        verify(traineeService, never()).update(any());
        assertNull(result);
    }

    // Test deleteTrainee
    @Test
    void shouldDeleteTraineeSuccessfully() throws EntityException, AccessException {
        String username = "john.doe";
        String executor = "executor";

        when(traineeService.delete(username)).thenReturn(true);

        boolean result = mainFacade.deleteTrainee(username, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, times(1)).delete(username);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenDeleteTraineeThrowsEntityException() throws AccessException, EntityException {
        String username = "john.doe";
        String executor = "executor";

        when(traineeService.delete(username)).thenThrow(new EntityException("Failed to delete"));

        boolean result = mainFacade.deleteTrainee(username, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(traineeService, times(1)).delete(username);
        assertFalse(result);
    }

    // Test createTrainer
    @Test
    void shouldCreateTrainerSuccessfully() throws GymException {
        when(trainerService.save(any(TrainerDto.class))).thenReturn(expectedTrainer);

        TrainerResponseDto result = mainFacade.createTrainer(trainerDto);

        verify(validator, times(1)).validate(trainerDto, Create.class);
        verify(trainerService, times(1)).save(trainerDto);
        assertEquals(expectedTrainer, result);
    }

    @Test
    void shouldReturnNullWhenCreateTrainerThrowsConstraintViolationException() throws GymException {
        doThrow(new ConstraintViolationException(null)).when(validator).validate(any(), any());

        TrainerResponseDto result = mainFacade.createTrainer(trainerDto);

        verify(validator, times(1)).validate(trainerDto, Create.class);
        verify(trainerService, never()).save(any());
        assertNull(result);
    }

    // Test findTrainerById
    @Test
    void shouldFindTrainerByIdSuccessfully() throws AccessException, EntityException {
        Long id = 1L;
        String executor = "executor";

        when(trainerService.findById(id)).thenReturn(expectedTrainer);

        TrainerResponseDto result = mainFacade.findTrainerById(id, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(trainerService, times(1)).findById(id);
        assertEquals(expectedTrainer, result);
    }

    @Test
    void shouldReturnNullWhenFindTrainerByIdThrowsEntityException() throws AccessException, EntityException {
        Long id = 1L;
        String executor = "executor";

        when(trainerService.findById(id)).thenThrow(new EntityException("Trainer not found"));

        TrainerResponseDto result = mainFacade.findTrainerById(id, executor);

        verify(manager, times(1)).checkPermissions(executor);
        verify(trainerService, times(1)).findById(id);
        assertNull(result);
    }

    // Test updateTrainer
    @Test
    void shouldUpdateTrainerSuccessfully() throws GymException {
        when(trainerService.update(any(TrainerDto.class))).thenReturn(expectedTrainer);

        TrainerResponseDto result = mainFacade.updateTrainer(trainerDto, "executor");

        verify(manager, times(1)).checkPermissions("executor");
        verify(validator, times(1)).validate(trainerDto, Create.class);
        verify(trainerService, times(1)).update(trainerDto);
        assertEquals(expectedTrainer, result);
    }

    @Test
    void shouldReturnNullWhenUpdateTrainerThrowsConstraintViolationException() throws GymException {
        doThrow(new ConstraintViolationException(null)).when(validator).validate(any(), any());

        TrainerResponseDto result = mainFacade.updateTrainer(trainerDto, "executor");

        verify(validator, times(1)).validate(trainerDto, Create.class);
        verify(trainerService, never()).update(any());
        assertNull(result);
    }
}
