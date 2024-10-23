package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.*;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.AccessException;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.security.AuthenticationManager;
import com.krasnopolskyi.service.impl.TraineeService;
import com.krasnopolskyi.service.impl.TrainerService;
import com.krasnopolskyi.service.impl.TrainingService;
import com.krasnopolskyi.service.impl.UserService;
import com.krasnopolskyi.validation.CommonValidator;
import com.krasnopolskyi.validation.group.Create;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class MainFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final CommonValidator validator;
    private final AuthenticationManager manager;
    private final UserService userService;

    public boolean changePassword(String password, String executor) {
        try {
            manager.checkPermissions(executor);
            userService.changePassword(executor, password);
            log.info("Password has been changed");
            return true;
        } catch (GymException e) {
            log.warn(e.getMessage());
        }
        return false;
    }

    public boolean changeActivityStatus(String target, String executor) {
        try {
            manager.checkPermissions(executor);
            User user = userService.changeActivityStatus(target);
            log.info("User " + user.getUsername()+ " is " + (user.getIsActive() ? "active" : "inactive"));
            return true;
        } catch (GymException e) {
            log.warn(e.getMessage());
        }
        return false;
    }

    public TraineeResponseDto createTrainee(TraineeDto traineeDto) {
        try {
            validator.validate(traineeDto, Create.class);
            TraineeResponseDto trainee = traineeService.save(traineeDto);
            log.info("Trainee saved successfully");
            return trainee;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
        }
        return null;
    }

    public TraineeResponseDto findTraineeByUsername(String username, String executor) {
        try {
            manager.checkPermissions(executor);
            TraineeResponseDto trainee = traineeService.findByUsername(username);
            log.info("trainee with " + username + " has been found");
            return trainee;
        } catch (EntityException e) {
            log.warn("Failed find trainee " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public TraineeResponseDto updateTrainee(TraineeDto traineeDto, String executor) {
        try {
            manager.checkPermissions(executor);
            validator.validate(traineeDto, Create.class);
            TraineeResponseDto refreshedTrainee = traineeService.update(traineeDto);
            log.info("Trainee successfully refreshed");
            return refreshedTrainee;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        } catch (GymException e) {
            log.warn("Failed update trainee " + traineeDto.getId());
        }
        return null;
    }

    public boolean deleteTrainee(String username, String executor) {
        try {
            manager.checkPermissions(executor);
            boolean isDeleted = traineeService.delete(username);
            log.info("trainee with " + username + " has been deleted");
            return isDeleted;
        } catch (EntityException e) {
            log.warn("Failed attempt to delete trainee " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        }
        return false;
    }
    ///////////////////////trainer///////////////////////////////////

    public TrainerResponseDto createTrainer(TrainerDto trainerDto) {
        try {
            validator.validate(trainerDto, Create.class);
            TrainerResponseDto trainer = trainerService.save(trainerDto);
            log.info("Trainer saved successfully");
            return trainer;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
        } catch (ValidateException | EntityException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public TrainerResponseDto updateTrainer(TrainerDto trainerDto, String executor) {
        try {
            manager.checkPermissions(executor);
            validator.validate(trainerDto, Create.class);
            TrainerResponseDto updatedTrainer = trainerService.update(trainerDto);
            log.info("Trainer successfully refreshed");
            return updatedTrainer;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        } catch (GymException e) {
            log.warn("Failed update trainee " + trainerDto.getId() + " " + e.getMessage());
        }
        return null;
    }

    public TrainerResponseDto findTrainerByUsername(String username, String executor) {
        try {
            manager.checkPermissions(executor);
            TrainerResponseDto trainer = trainerService.findByUsername(username);
            log.info("trainer with " + username + " has been found");
            return trainer;
        } catch (EntityException e) {
            log.warn("Failed find trainer " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        }
        return null;
    }
    //////////////////////////////////////////////////////////////

    public TrainingResponseDto addTraining(TrainingDto trainingDto, String executor) {
        try {
            manager.checkPermissions(executor);
            validator.validate(trainingDto, Create.class);
            TrainingResponseDto training = trainingService.save(trainingDto);
            log.info("Trainining saved successfully");
            return training;
        } catch (AccessException e) {
            log.warn(e.getMessage());
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
        } catch (ValidateException e) {
            log.warn("adding training session failed " + e.getMessage());
        }  catch (EntityException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public List<TrainingResponseDto> getAllTrainingsByUsernameAndFilter(TrainingFilterDto filterDto, String executor) {
        try {
            manager.checkPermissions(executor);
            validator.validate(filterDto, Create.class);
            return trainingService.getFilteredTrainings(filterDto);
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        } catch (EntityException e) {
            log.warn(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<TrainerResponseDto> getAllNotAssignedTrainersByTraineeUsername(String username, String executor) {
        try {
            manager.checkPermissions(executor);
            return traineeService.findAllNotAssignedTrainersByTrainee(username);
        } catch (EntityException e) {
            log.warn("Something went wrong " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<TrainerResponseDto> updateTrainers(TraineeDto trainee, List<TrainerDto> newTrainers, String executor) {
        try {
            manager.checkPermissions(executor);
            return traineeService.updateTrainers(trainee, newTrainers);
        } catch (EntityException e) {
            log.warn("Could not update trainers list " + e.getMessage());
        } catch (AccessException e) {
            log.warn(e.getMessage());
        }
        return new ArrayList<>();
    }
}
