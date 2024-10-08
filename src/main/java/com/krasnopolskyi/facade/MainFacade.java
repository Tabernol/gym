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

    public UserCredentials createTrainee(TraineeDto traineeDto) {
        return traineeService.save(traineeDto);
    }

    public Trainee findTraineeById(Long id) throws EntityNotFoundException {
        return traineeService.findById(id);
    }

    public Trainee updateTrainee(Trainee trainee){
        return traineeService.update(trainee);
    }

    public boolean deleteTrainee(Trainee trainee){
        return traineeService.delete(trainee);
    }
    //////////////////////////////////////////////////////////

    public UserCredentials createTrainer(TrainerDto trainerDto){
        try {
            return trainerService.save(trainerDto);
        } catch (ValidateException e) {
            // here should be ExceptionHandler
            log.warn("Registration failed wrong type of specialization");
            return new UserCredentials("","");
        }
    }

    public Trainer findTrainerById(Long id) throws EntityNotFoundException {
        return trainerService.findById(id);
    }

    public Trainer updateTrainer(Trainer trainer){
        return trainerService.update(trainer);
    }
    //////////////////////////////////////////////////////////////

    public Training addTraining(TrainingDto trainingDto){
        try {
            return trainingService.save(trainingDto);
        } catch (ValidateException e) {
            // here should be ExceptionHandler
            log.info("adding training session failed " + e.getMessage());
            return null;
        }
    }

    public Training findTrainingById(Long id) throws EntityNotFoundException {
        return trainingService.findById(id);
    }
}
