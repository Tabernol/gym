package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.*;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingService;
import com.krasnopolskyi.validation.CommonValidator;
import com.krasnopolskyi.validation.Create;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class MainFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final CommonValidator validator;

    public TraineeResponseDto createTrainee(TraineeDto traineeDto) {
        try {
            validator.validate(traineeDto, Create.class);
            TraineeResponseDto trainee = traineeService.save(traineeDto);
            log.info("Trainee saved successfully");
            return trainee;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
            return null;
        } catch (GymException e) {
            log.warn("Registration failed " + e.getMessage());
            return null;
        }
    }

    public TraineeResponseDto findTraineeById(Long id) {
        try {
            TraineeResponseDto trainee = traineeService.findById(id);
            log.info("trainee with " + id + " has been found");
            return trainee;
        } catch (EntityException e) {
            log.info("Failed find trainee " + e.getMessage());
            return null;
        }
    }

    public TraineeResponseDto findTraineeByUsername(String username) {
        try {
            TraineeResponseDto trainee = traineeService.findByUsername(username);
            log.info("trainee with " + username + " has been found");
            return trainee;
        } catch (EntityException e) {
            log.info("Failed find trainee " + e.getMessage());
            return null;
        }
    }

    public TraineeResponseDto updateTrainee(TraineeDto traineeDto) {
        try {
            validator.validate(traineeDto, Create.class);
            TraineeResponseDto refreshedTrainee = traineeService.update(traineeDto);
            log.info("Trainee successfully refreshed");
            return refreshedTrainee;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
            return null;
        } catch (GymException e) {
            log.info("Failed update trainee " + traineeDto.getId());
            throw null;
        }
    }

    public boolean deleteTrainee(String username) {
        try {
            boolean isDeleted = traineeService.delete(username);
            log.info("trainee with " + username + " has been deleted");
            return isDeleted;
        } catch (EntityException e) {
            log.info("Failed attempt to delete trainee " + e.getMessage());
            return false;
        }
    }
    //////////////////////////////////////////////////////////

    public TrainerResponseDto createTrainer(TrainerDto trainerDto) {
        try {
            validator.validate(trainerDto, Create.class);
            TrainerResponseDto trainer = trainerService.save(trainerDto);
            log.info("Trainer saved successfully");
            return trainer;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
            return null;
        } catch (GymException e) {
            // here should be ExceptionHandler
            log.warn("Registration failed " + e.getMessage());
            return null;
        }
    }

    public TrainerResponseDto findTrainerById(Long id) {
        try {
            TrainerResponseDto maybeTrainer = trainerService.findById(id);
            log.info("trainee with " + id + " has been found");
            return maybeTrainer;
        } catch (EntityException e) {
            log.info("Failed find trainee " + e.getMessage());
            return null;
        }
    }

    public TrainerResponseDto updateTrainer(TrainerDto trainerDto) {
        try {
            validator.validate(trainerDto, Create.class);
            TrainerResponseDto updatedTrainer = trainerService.update(trainerDto);
            log.info("Trainer successfully refreshed");
            return updatedTrainer;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
            return null;
        } catch (GymException e) {
            log.info("Failed update trainee " + trainerDto.getId());
            return null;
        }
    }
    //////////////////////////////////////////////////////////////

    public TrainingResponseDto addTraining(TrainingDto trainingDto) {
        try {
            validator.validate(trainingDto, Create.class);
            TrainingResponseDto training = trainingService.save(trainingDto);
            log.info("Trainining saved successfully");
            return training;
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
            return null;
        } catch (ValidateException e) {
            // here should be ExceptionHandler
            log.warn("adding training session failed " + e.getMessage());
            return null;
        }
    }

    public TrainingResponseDto findTrainingById(Long id) {
        try {
            TrainingResponseDto trainingResponseDto = trainingService.findById(id);
            log.info("training with " + id + " has been found");
            return trainingResponseDto;
        } catch (EntityException e) {
            log.info("Failed find training " + e.getMessage());
            return null;
        }
    }

    public List<TrainingResponseDto> getAllTrainingsByUsernameAndFilter(TrainingFilterDto filterDto) {
        try {
            validator.validate(filterDto, Create.class);
            return trainingService.getFilteredTrainings(filterDto);
        } catch (ConstraintViolationException e) {
            log.warn("Validation failed: " + e.getMessage());
            return null;
        } catch (ValidateException e) {
            log.info("Something went wrong " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<TrainerResponseDto> getAllNotAssignedTrainersByTraineeUsername(String username) {
        try {
            return traineeService.findAllNotAssignedTrainersByTrainee(username);
        } catch (EntityException e) {
            log.info("Something went wrong " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<TrainerResponseDto> updateTrainers(TraineeDto trainee, List<TrainerDto> newTrainers) {
        try {
            return traineeService.updateTrainers(trainee, newTrainers);
        } catch (EntityException e) {
            log.info("Could not update trainers list " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
