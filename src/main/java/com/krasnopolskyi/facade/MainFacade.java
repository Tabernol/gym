package com.krasnopolskyi.facade;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingService;
import org.springframework.stereotype.Component;

@Component
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
        return trainerService.save(trainerDto);
    }

    public Trainer findTrainerById(Long id) throws EntityNotFoundException {
        return trainerService.findById(id);
    }

    public Trainer updateTrainer(Trainer trainer){
        return trainerService.update(trainer);
    }
    //////////////////////////////////////////////////////////////

    public Training addTraining(Training training){
        return trainingService.save(training);
    }

    public Training findTrainingById(Long id) throws EntityNotFoundException {
        return trainingService.findById(id);
    }
}
