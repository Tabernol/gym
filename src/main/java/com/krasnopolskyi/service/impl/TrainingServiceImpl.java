package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TraineeRepository;
import com.krasnopolskyi.database.dao.TrainerRepository;
import com.krasnopolskyi.database.dao.TrainingRepository;
import com.krasnopolskyi.database.dao.TrainingTypeRepository;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TrainingService;
import com.krasnopolskyi.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    public Training save(TrainingDto trainingDto) throws ValidateException {
        Trainee trainee = traineeRepository.findById(trainingDto.getTraineeId())
                .orElseThrow(() -> new ValidateException("Could not find trainee with id " + trainingDto.getTraineeId()));

        Trainer trainer = trainerRepository.findById(trainingDto.getTrainerId())
                .orElseThrow(() -> new ValidateException("Could not find trainer with id " + trainingDto.getTrainerId()));

        TrainingType trainingType = trainingTypeRepository.findById(trainingDto.getTrainingType())
                .orElseThrow(() -> new ValidateException("Could not find training type with id " + trainingDto.getTrainingType()));

        if(trainer.getSpecialization() != trainingType.getId()){
            log.warn("Attempt to save training session with wrong specialization for trainer");
            throw new ValidateException("This trainer is not assigned to this training type");
        }

        long id = IdGenerator.generateId();
        Training training = Training.builder()
                .id(id)
                .traineeId(trainee.getId())
                .trainerId(trainer.getId())
                .trainingName(trainingType.getType())
                .date(trainingDto.getDate())
                .duration(trainingDto.getDuration())
                .build();
        Training save = trainingRepository.save(training);
        log.debug("training has been saved " + save);
        return save;
    }

    @Override
    public Training findById(Long id) throws EntityNotFoundException {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found training with id " + id));
    }
}
