package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.repository.impl.TrainerRepositoryImpl;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
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
    public UserCredentials save(TrainerDto trainerDto) throws ValidateException {
        int specialization;
        try {
            specialization = trainingTypeService.findById(trainerDto.getSpecialization()).getId();
        } catch (EntityException e) {
            log.warn("Attempt to save trainer with wrong specialization " + trainerDto.getSpecialization());
            throw new ValidateException("Specialisation with id " + trainerDto.getSpecialization() + " does not exist");
        }

        User user = userService.create(new UserDto(trainerDto.getFirstName(), trainerDto.getLastName()));

//        trainerRepository.save(trainer); // save entity
//        log.info("trainer has been saved " + trainer.getId());
        return new UserCredentials("", "");
    }

    @Override
    public Trainer findById(Long id) throws EntityException {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found trainer with id " + id));
    }

    @Override
    public Trainer update(Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
