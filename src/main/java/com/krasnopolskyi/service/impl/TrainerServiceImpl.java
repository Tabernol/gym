package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.repository.impl.TrainerRepositoryImpl;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.GymException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingTypeService;
import com.krasnopolskyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    // initialized via autowired because task condition 4
    // I prefer initialized via constructor
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainingTypeService trainingTypeService;

    @Override
    public TrainerResponseDto save(TrainerDto trainerDto) throws GymException {
        validate(trainerDto); // validate specialization
        TrainingType specialization = trainingTypeService.findById(trainerDto.getSpecialization()); // receive specialization

        User savedUser = userService
                .create(new UserDto(trainerDto.getFirstName(),
                        trainerDto.getLastName())); //return user with id, username and password
        Trainer trainer = new Trainer();
        trainer.setUser(savedUser);
        trainer.setSpecialization(specialization);

        trainerRepository.save(trainer);// save entity
        log.debug("trainer has been saved " + trainer.getId());
        return mapToDto(specialization, savedUser);
    }

    @Override
    public TrainerResponseDto findById(Long id) throws EntityException {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found trainer with id " + id));
        User user = userService.findById(trainer.getUser().getId()); // find user associated with trainer
        TrainingType specialization = trainingTypeService.findById(trainer.getSpecialization().getId()); // find specialization of trainee

        return mapToDto(specialization, user);
    }

    @Override
    public TrainerResponseDto findByUsername(String username) throws GymException {
        return null;
    }

    @Override
    public TrainerResponseDto update(TrainerDto trainerDto) throws GymException {
        validate(trainerDto); // validate specialization
        Trainer trainer = trainerRepository.findById(trainerDto.getId())
                .orElseThrow(() -> new EntityException("Could not found trainer with id " + trainerDto.getId()));
        //update user's fields
        User user = userService.findById(trainer.getUser().getId()); // pass refreshed user to repository
        user.setFirstName(trainerDto.getFirstName());
        user.setLastName(trainerDto.getLastName());
        userService.update(user);
        //update trainer's fields
        trainer.setSpecialization(trainer.getSpecialization());
        Trainer saveDTrainer = trainerRepository.save(trainer); // pass refreshed trainer to repository
        TrainingType newSpecialization = trainingTypeService.findById(saveDTrainer.getSpecialization().getId()); // received specialization
        log.debug("trainer has been updated " + trainer.getId());
        return mapToDto(newSpecialization, user);
    }


    private void validate(TrainerDto trainerDto) throws ValidateException {
        try {
            trainingTypeService.findById(trainerDto.getSpecialization());
        } catch (EntityException e) {
            log.debug("Attempt to save trainer with wrong specialization " + trainerDto.getSpecialization());
            throw new ValidateException("Specialisation with id " + trainerDto.getSpecialization() + " does not exist");
        }
    }

    private TrainerResponseDto mapToDto(TrainingType specialization, User user) {
        return new TrainerResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                specialization.getType());
    }
}
