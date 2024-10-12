package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.repository.TrainerRepository;
import com.krasnopolskyi.repository.TrainingRepository;
import com.krasnopolskyi.repository.TrainingTypeRepository;
import com.krasnopolskyi.repository.impl.TrainerRepositoryImpl;
import com.krasnopolskyi.repository.impl.TrainingRepositoryImpl;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.Training;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    @Override
    public Training save(TrainingDto trainingDto) throws ValidateException {
        Trainee trainee = traineeRepository.findById(trainingDto.getTraineeId())
                .orElseThrow(() -> new ValidateException("Could not find trainee with id " + trainingDto.getTraineeId()));

//        Trainer trainer = trainerRepository.findById(trainingDto.getTrainerId())
//                .orElseThrow(() -> new ValidateException("Could not find trainer with id " + trainingDto.getTrainerId()));
//
//        TrainingType trainingType = trainingTypeRepository.findById(trainingDto.getTrainingType())
//                .orElseThrow(() -> new ValidateException("Could not find training type with id " + trainingDto.getTrainingType()));

//        if(trainer.getSpecialization() != trainingType.getId()){
//            log.debug("Attempt to save training session with wrong specialization for trainer");
//            throw new ValidateException("This trainer is not assigned to this training type");
//        }


        log.info("training has been saved ");
        return new Training();
    }

    @Override
    public Training findById(Long id) throws EntityException {
        return new Training();
//        return trainingRepository.findById(id)
//                .orElseThrow(() -> new EntityException("Could not found training with id " + id));
    }
}
