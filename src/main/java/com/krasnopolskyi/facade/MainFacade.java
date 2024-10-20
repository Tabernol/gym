package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public MainFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public TraineeResponseDto createTrainee(TraineeDto traineeDto) {
        try {
            TraineeResponseDto trainee = traineeService.save(traineeDto);
            log.info("Trainee saved successfully");
            return trainee;
        } catch (ValidateException e) {
            log.warn("Registration failed " + e.getMessage());
            return null;
        }
    }

    public TraineeResponseDto findTraineeById(Long id) {
        try {
            TraineeResponseDto trainee = traineeService.findById(id);
            log.info("trainee with " + id + " has been found");
            return trainee;
        } catch (EntityNotFoundException e) {
            log.info("Failed find trainee " + e.getMessage());
            return null;
        }
    }

    public TraineeResponseDto updateTrainee(TraineeDto traineeDto) {
        try {
            TraineeResponseDto refreshedTrainee = traineeService.update(traineeDto);
            log.info("Trainee successfully refreshed");
            return refreshedTrainee;
        } catch (EntityNotFoundException e) {
            log.info("Failed update trainee " + traineeDto.getId());
            throw null;
        }
    }

    public boolean deleteTrainee(TraineeDto traineeDto) {
        try {
            boolean isDeleted = traineeService.delete(traineeDto);
            log.info("trainee with " + traineeDto.getId() + " has been deleted");
            return isDeleted;
        } catch (EntityNotFoundException e) {
            log.info("Failed attempt to delete trainee " + e.getMessage());
            return false;
        }
    }
    //////////////////////////////////////////////////////////

    public TrainerResponseDto createTrainer(TrainerDto trainerDto){
        try {
            TrainerResponseDto trainer = trainerService.save(trainerDto);
            log.info("Trainer saved successfully");
            return trainer;
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
        } catch (EntityNotFoundException e) {
            log.info("Failed find trainee " + e.getMessage());
            return null;
        }
    }

    public TrainerResponseDto updateTrainer(TrainerDto trainerDto) {
        try {
            TrainerResponseDto updatedTrainer = trainerService.update(trainerDto);
            log.info("Trainer successfully refreshed");
            return updatedTrainer;
        } catch (GymException e) {
            log.info("Failed update trainee " + trainerDto.getId());
            throw null;
        }
    }
    //////////////////////////////////////////////////////////////

    public Training addTraining(TrainingDto trainingDto){
        try {
            Training training = trainingService.save(trainingDto);
            log.info("Trainining saved successfully");
            return training;
        } catch (ValidateException e) {
            // here should be ExceptionHandler
            log.warn("adding training session failed " + e.getMessage());
            return null;
        }
    }

    public Training findTrainingById(Long id) {
        try {
            Training training = trainingService.findById(id);
            log.info("training with " + id + " has been found");
            return training;
        } catch (EntityNotFoundException e) {
            log.info("Failed find training " + e.getMessage());
            return null;
        }
    }
}
