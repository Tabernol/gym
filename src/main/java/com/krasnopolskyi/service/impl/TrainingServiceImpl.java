package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.TrainingFilterDto;
import com.krasnopolskyi.dto.response.TrainingResponseDto;
import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.repository.*;
import com.krasnopolskyi.dto.request.TrainingDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TrainingService;
import com.krasnopolskyi.utils.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public TrainingResponseDto save(TrainingDto trainingDto) throws ValidateException, EntityException {
        Trainee trainee = traineeRepository.findById(trainingDto.getTraineeId())
                .orElseThrow(() -> new EntityException("Could not find trainee with id " + trainingDto.getTraineeId()));

        Trainer trainer = trainerRepository.findById(trainingDto.getTrainerId())
                .orElseThrow(() -> new EntityException("Could not find trainer with id " + trainingDto.getTrainerId()));

        TrainingType trainingType = trainingTypeRepository.findById(trainingDto.getTrainingType())
                .orElseThrow(() -> new EntityException("Could not find training type with id " + trainingDto.getTrainingType()));

        if (trainer.getSpecialization().getId() != trainingType.getId()) {
            log.debug("Attempt to save training session with wrong specialization for trainer");
            throw new ValidateException("This trainer is not assigned to this training type");
        }
        trainer.getTrainees().add(trainee); // save into set and table trainer_trainee

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setDate(trainingDto.getDate());
        training.setDuration(trainingDto.getDuration());
        training.setTrainingName(trainingDto.getTrainingName());

        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);

        trainingRepository.save(training);

        return TrainingMapper.mapToDto(training);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingResponseDto findById(Long id) throws EntityException {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found training with id " + id));
        return TrainingMapper.mapToDto(training);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingResponseDto> getFilteredTrainings(TrainingFilterDto filter) throws EntityException {
        userRepository.findByUsername(filter.getOwner())
                .orElseThrow(() -> new EntityException("Could not found user: " + filter.getOwner()));

        List<Training> trainings = trainingRepository.getFilteredTrainings(filter);

        return trainings.stream().map(TrainingMapper::mapToDto).collect(Collectors.toList());
    }
}
