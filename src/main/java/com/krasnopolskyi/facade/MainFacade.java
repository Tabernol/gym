package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
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
            return traineeService.save(traineeDto);
        } catch (ValidateException e) {
            log.warn("Registration failed " + e.getMessage());
            return null;
        }
    }

    public TraineeResponseDto findTraineeById(Long id) throws EntityNotFoundException {
        return traineeService.findById(id);
    }

    public TraineeResponseDto updateTrainee(TraineeDto traineeDto) throws EntityNotFoundException {
        return traineeService.update(traineeDto);
    }

    public boolean deleteTrainee(TraineeDto traineeDto) throws EntityNotFoundException {
        return traineeService.delete(traineeDto);
    }
    //////////////////////////////////////////////////////////

    public TrainerResponseDto createTrainer(TrainerDto trainerDto){
        try {
            return trainerService.save(trainerDto);
        } catch (GymException e) {
            // here should be ExceptionHandler
            log.warn("Registration failed " + e.getMessage());
            return null;
        }
    }

    public TrainerResponseDto findTrainerById(Long id) throws EntityNotFoundException {
        return trainerService.findById(id);
    }

    public TrainerResponseDto updateTrainer(TrainerDto trainerDto) throws GymException {
        return trainerService.update(trainerDto);
    }
    //////////////////////////////////////////////////////////////

    public Training addTraining(TrainingDto trainingDto){
        try {
            return trainingService.save(trainingDto);
        } catch (ValidateException e) {
            // here should be ExceptionHandler
            log.warn("adding training session failed " + e.getMessage());
            return null;
        }
    }

    public Training findTrainingById(Long id) throws EntityNotFoundException {
        return trainingService.findById(id);
    }
}
