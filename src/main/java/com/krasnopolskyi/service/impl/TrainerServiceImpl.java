package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TrainerRepository;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TrainerService;
import com.krasnopolskyi.service.TrainingTypeService;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {
    // initialized via autowired because task condition 4
    // I prefer initialized via constructor
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainingTypeService trainingTypeService;

    @Override
    public UserCredentials save(TrainerDto trainerDto) {
        int specialization;
        try {
            specialization = trainingTypeService.findById(trainerDto.getSpecialization()).getId();
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Specialisation with id " + trainerDto.getSpecialization() + " does not exist");
        }

        User user = userService.save(new UserDto(trainerDto.getFirstName(), trainerDto.getLastName()));
        long id = IdGenerator.generateId();
        Trainer trainer = Trainer.builder()
                .id(id)
                .userId(user.getId())
                .specialization(specialization)
                .build();
        trainerRepository.save(trainer); // save entity

        return new UserCredentials(user.getLogin(), user.getPassword());
    }

    @Override
    public Trainer findById(Long id) throws EntityNotFoundException {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found trainer with id " + id));
    }

    @Override
    public Trainer update(Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
