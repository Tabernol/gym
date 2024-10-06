package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TraineeRepository;
import com.krasnopolskyi.database.dao.TrainerRepository;
import com.krasnopolskyi.database.dao.TrainingRepository;
import com.krasnopolskyi.database.dao.TrainingTypeRepository;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TrainingService;
import com.krasnopolskyi.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {

    // initialized via autowired because task condition 4
    // I prefer initialized via constructor
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Override
    public Training save(Training training) {
        Trainee trainee = traineeRepository.findById(training.getTraineeId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find trainee with id " + training.getTraineeId()));

        Trainer trainer = trainerRepository.findById(training.getTrainerId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find trainer with id " + training.getTrainerId()));

        TrainingType trainingType = trainingTypeRepository.findById(training.getTrainingType())
                .orElseThrow(() -> new IllegalArgumentException("Could not find training type with id " + training.getTrainingType()));

        long id = IdGenerator.generateId();
        Training.builder()
                .id(id)
                .traineeId(trainee.getId())
                .trainerId(trainer.getId())
                .trainingName(trainingType.getType())
                .date(training.getDate())
                .duration(training.getDuration())
                .build();
        return trainingRepository.save(training);
    }

    @Override
    public Training findById(Long id) throws EntityNotFoundException {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found training with id " + id));
    }

    private void validate(Training training) {
        traineeRepository.findById(training.getTraineeId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find trainee with id " + training.getTraineeId()));

        trainerRepository.findById(training.getTrainerId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find trainer with id " + training.getTrainerId()));

        trainingTypeRepository.findById(training.getTrainingType())
                .orElseThrow(() -> new IllegalArgumentException("Could not find training type with id " + training.getTrainingType()));
    }
}
