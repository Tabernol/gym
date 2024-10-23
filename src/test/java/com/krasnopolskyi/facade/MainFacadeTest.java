package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.request.TrainingFilterDto;
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


//    @Test
//    void shouldNotChangePasswordDueToAccessException() throws GymException {
//        String newPassword = "newPassword123";
//        String executor = "executor";
//
//        doThrow(new AccessException("No permission")).when(manager).checkPermissions(executor);
//
//        boolean result = mainFacade.changePassword(newPassword, executor);
//
//        verify(manager, times(1)).checkPermissions(executor);
//        verify(userService, never()).findByUsername(anyString());
//        verify(userService, never()).changePassword(any(User.class), anyString());
//        assertFalse(result);
//    }

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
    void shouldAddTrainingSuccessfully() throws AccessException, ValidateException, EntityException {
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
    void shouldNotAddTrainingDueToValidationFailure() throws ValidateException, AccessException, EntityException {
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





    @Test
    void testChangeActivityStatus_Success() throws GymException {
        String target = "targetUser";
        String executor = "executorUser";

        doNothing().when(manager).checkPermissions(executor);
        User user = mock(User.class);
        when(userService.changeActivityStatus(target)).thenReturn(user);
        when(user.getUsername()).thenReturn(target);
        when(user.getIsActive()).thenReturn(true); // Assuming the user is active

        boolean result = mainFacade.changeActivityStatus(target, executor);

        assertTrue(result);
        verify(manager).checkPermissions(executor);
        verify(userService).changeActivityStatus(target);
    }



    @Test
    void testFindTrainerByUsername_Success() throws EntityException, AccessException {
        String username = "trainerUser";
        String executor = "executorUser";

        doNothing().when(manager).checkPermissions(executor);
        when(trainerService.findByUsername(username)).thenReturn(expectedTrainer);

        TrainerResponseDto result = mainFacade.findTrainerByUsername(username, executor);

        assertNotNull(result);
        verify(manager).checkPermissions(executor);
        verify(trainerService).findByUsername(username);
    }

    @Test
    void testFindTrainerByUsername_Failure() throws EntityException, AccessException {
        String username = "trainerUser";
        String executor = "executorUser";

        doNothing().when(manager).checkPermissions(executor);
        doThrow(new EntityException("Trainer not found")).when(trainerService).findByUsername(username);

        TrainerResponseDto result = mainFacade.findTrainerByUsername(username, executor);

        assertNull(result);
        verify(manager).checkPermissions(executor);
        verify(trainerService).findByUsername(username);
    }

    @Test
    void testGetAllTrainingsByUsernameAndFilter_Success() throws EntityException, AccessException {
        String executor = "executorUser";
        TrainingFilterDto filterDto = TrainingFilterDto.builder().owner("john.doe").build();
        when(trainingService.getFilteredTrainings(filterDto)).thenReturn(new ArrayList<>());

        doNothing().when(manager).checkPermissions(executor);
//        when(validator.validate(filterDto, Create.class)).thenReturn(null);

        List<TrainingResponseDto> result = mainFacade.getAllTrainingsByUsernameAndFilter(filterDto, executor);

        assertNotNull(result);
        verify(manager).checkPermissions(executor);
        verify(trainingService).getFilteredTrainings(filterDto);
    }

    @Test
    void testGetAllTrainingsByUsernameAndFilter_Failure() throws EntityException, AccessException {
        String executor = "executorUser";
        TrainingFilterDto filterDto = TrainingFilterDto.builder().owner("john.doe").build();
        doThrow(new AccessException("Access denied")).when(manager).checkPermissions(executor);

        List<TrainingResponseDto> result = mainFacade.getAllTrainingsByUsernameAndFilter(filterDto, executor);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(manager).checkPermissions(executor);
        verify(trainingService, never()).getFilteredTrainings(any());
    }

    @Test
    void testGetAllNotAssignedTrainersByTraineeUsername_Success() throws EntityException, AccessException {
        String username = "traineeUser";
        String executor = "executorUser";
        List<TrainerResponseDto> trainers = new ArrayList<>();
        trainers.add(expectedTrainer);

        doNothing().when(manager).checkPermissions(executor);
        when(traineeService.findAllNotAssignedTrainersByTrainee(username)).thenReturn(trainers);

        List<TrainerResponseDto> result = mainFacade.getAllNotAssignedTrainersByTraineeUsername(username, executor);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(manager).checkPermissions(executor);
        verify(traineeService).findAllNotAssignedTrainersByTrainee(username);
    }


    @Test
    void testUpdateTrainers_Success() throws EntityException, AccessException {
        String executor = "executorUser";
        List<TrainerResponseDto> trainers = new ArrayList<>();
        trainers.add(expectedTrainer);

        doNothing().when(manager).checkPermissions(executor);
        when(traineeService.updateTrainers(traineeDto, new ArrayList<>())).thenReturn(List.of(expectedTrainer));

        List<TrainerResponseDto> result = mainFacade.updateTrainers(traineeDto, new ArrayList<>(), executor);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(manager).checkPermissions(executor);
        verify(traineeService).updateTrainers(traineeDto, new ArrayList<>());
    }


    @Test
    void changeActivityStatus() throws GymException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when((userService.changeActivityStatus(any(String.class)))).thenThrow(new EntityException("Could not found user: "));

        assertFalse(mainFacade.changeActivityStatus("target", "executor"));
    }

    @Test
    public void changePassword() throws GymException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(userService.changePassword("target", "executor")).thenReturn(new User());

        assertTrue(mainFacade.changePassword("target", "executor"));
    }
    @Test
    public void updateTraineeTestAccessFailed() throws AccessException {
        String executor = "executor";
        doThrow(new AccessException("")).when(manager).checkPermissions(any(String.class));

        assertNull(mainFacade.updateTrainee(TraineeDto.builder().build(), "executor"));
    }

    @Test
    public void updateTrainerTestAccessFailed() throws AccessException {
        String executor = "executor";
        doThrow(new AccessException("")).when(manager).checkPermissions(any(String.class));

        assertNull(mainFacade.updateTrainer(TrainerDto.builder().build(), "executor"));
    }



    @Test
    public void deleteTraineeTestAccessFailed() throws AccessException {
        String executor = "executor";
        doThrow(new AccessException("")).when(manager).checkPermissions(any(String.class));

        assertFalse(mainFacade.deleteTrainee("target", "executor"));
    }

    @Test
    public void createTrainerEntityException() throws GymException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(trainerService.save(trainerDto)).thenThrow(new EntityException(""));

        assertNull(mainFacade.createTrainer(TrainerDto.builder().build()), "executor");
    }

    @Test
    public void updateTrainerEntityException() throws GymException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(trainerService.update(trainerDto)).thenThrow(new EntityException(""));

        assertNull(mainFacade.updateTrainer(TrainerDto.builder().build(), "executor"));
    }

    @Test
    public void findTrainerByUsernameAccessFailed() throws AccessException {
        String executor = "executor";
        doThrow(new AccessException("")).when(manager).checkPermissions(any(String.class));

        assertNull(mainFacade.findTrainerByUsername("target", "executor"));
    }

    @Test
    public void addTrainingTestAccessFailed() throws AccessException {
        String executor = "executor";
        doThrow(new AccessException("")).when(manager).checkPermissions(any(String.class));

        assertNull(mainFacade.addTraining(TrainingDto.builder().build(), "executor"));
    }

    @Test
    public void AddTrainingValidateException1() throws GymException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(trainingService.save(TrainingDto.builder().build())).thenThrow(new ValidateException(""));

        assertNull(mainFacade.addTraining(TrainingDto.builder().build().builder().build(), "executor"));
    }

    @Test
    public void AddTrainingValidateException2() throws GymException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(trainingService.save(TrainingDto.builder().build())).thenThrow(new EntityException(""));

        assertNull(mainFacade.addTraining(TrainingDto.builder().build(), "executor"));
    }
    @Test
    public void updateTrainersEntityException() throws AccessException, EntityException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(traineeService.updateTrainers(any(TraineeDto.class), anyList())).thenThrow(new EntityException(""));

        assertEquals(new ArrayList<>(), mainFacade.updateTrainers(TraineeDto.builder().build(), List.of(TrainerDto.builder().build()), "executor"));
    }
    @Test
    public void getAllNotAssignedTrainersByTraineeUsernameTest() throws AccessException, EntityException {
        String executor = "executor";
        doNothing().when(manager).checkPermissions(executor);
        when(traineeService.findAllNotAssignedTrainersByTrainee(anyString())).thenThrow(new EntityException(""));
        assertEquals(new ArrayList<>(), mainFacade.getAllNotAssignedTrainersByTraineeUsername("test", "exuctor"));
    }

    @Test
    public void getAllNotAssignedTrainersByTraineeUsernameTest2() throws AccessException, EntityException {
        String executor = "executor";
        doThrow(new AccessException("")).when(manager).checkPermissions(any(String.class));
        assertEquals(new ArrayList<>(), mainFacade.getAllNotAssignedTrainersByTraineeUsername("test", "exuctor"));
    }
}
