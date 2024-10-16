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
import com.krasnopolskyi.utils.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepositoryImpl trainerRepository;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    @Override
    @Transactional
    public TrainerResponseDto save(TrainerDto trainerDto) throws GymException {
        validate(trainerDto); // validate specialization
        TrainingType specialization = trainingTypeService.findById(trainerDto.getSpecialization()); // receive specialization

        User newUser = userService
                .create(new UserDto(trainerDto.getFirstName(),
                        trainerDto.getLastName())); //return user with firstName, lastName, username, password, isActive
        Trainer trainer = new Trainer();
        trainer.setUser(newUser);
        trainer.setSpecialization(specialization);

        Trainer saveTrainer = trainerRepository.save(trainer);// save entity
        log.debug("trainer has been saved " + trainer.getId());
        return TrainerMapper.mapToDto(saveTrainer);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponseDto findById(Long id) throws EntityException {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found trainer with id " + id));
        return TrainerMapper.mapToDto(trainer);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponseDto findByUsername(String username) throws EntityException {
        return trainerRepository.findByUsername(username)
                .map(trainer -> TrainerMapper.mapToDto(trainer))
                .orElseThrow(() -> new EntityException("Can't find trainer with username " + username));
    }

    @Override
    @Transactional
    public TrainerResponseDto update(TrainerDto trainerDto) throws GymException {
        Trainer trainer = trainerRepository.findById(trainerDto.getId())
                .orElseThrow(() -> new EntityException("Could not found trainer with id " + trainerDto.getId()));
        //update user's fields
        User user = userService.findById(trainer.getUser().getId()); // pass refreshed user to repository
        user.setFirstName(trainerDto.getFirstName()); // here I change user's field and them will be safe if CascadeType.ALL
        user.setLastName(trainerDto.getLastName());
//        trainer.setUser(user); // what is better use CascadeType or set it manual
        //update trainer's fields
        if(trainerDto.getSpecialization() != null){
            TrainingType specialization = trainingTypeService.findById(trainerDto.getSpecialization());
            trainer.setSpecialization(specialization);
        }
        Trainer savedTrainer = trainerRepository.save(trainer); // pass refreshed trainer to repository
        log.info("trainer has been updated " + trainer.getId());
        return TrainerMapper.mapToDto(savedTrainer);
    }


    private void validate(TrainerDto trainerDto) throws ValidateException {
        try {
            trainingTypeService.findById(trainerDto.getSpecialization());
        } catch (EntityException e) {
            log.debug("Attempt to save trainer with does not exist specialization " + trainerDto.getSpecialization());
            throw new ValidateException("Specialisation with id " + trainerDto.getSpecialization() + " does not exist");
        }
    }
}
