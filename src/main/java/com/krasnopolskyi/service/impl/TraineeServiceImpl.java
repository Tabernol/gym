package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TraineeRepository;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final UserService userService;

    @Override
    public TraineeResponseDto save(TraineeDto traineeDto) throws ValidateException {
        User savedUser = userService
                .save(new UserDto(traineeDto.getFirstName(),
                        traineeDto.getLastName())); //return user with id, username and password
        long id = IdGenerator.generateId(); // generate if for trainee
        Trainee trainee = Trainee.builder()
                .id(id)
                .userId(savedUser.getId())
                .address(traineeDto.getAddress())
                .dateOfBirth(traineeDto.getDateOfBirth())
                .build();
        Trainee saveTrainee = traineeRepository.save(trainee); // pass to repository
        log.debug("trainee has been saved " + trainee.getId());
        return mapToDto(saveTrainee, savedUser); // mapping
    }

    @Override
    public TraineeResponseDto findById(Long id) throws EntityNotFoundException {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found trainee with id " + id)); // find trainee entity
        User user = userService.findById(trainee.getUserId()); // find user associated with trainee
        return mapToDto(trainee, user); // mapping

    }

    @Override
    public TraineeResponseDto update(TraineeDto traineeDto) throws EntityNotFoundException {
        // find trainee entity
        Trainee trainee = traineeRepository.findById(traineeDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Could not found trainee with id " + traineeDto.getId()));
        //update trainee's fields
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        Trainee savedTrainee = traineeRepository.save(trainee); // pass trainee to repository

        User user = userService.findById(trainee.getUserId()); // find user associated with trainee
        //update user's fields
        user.setFirstName(traineeDto.getFirstName());
        user.setLastName(traineeDto.getLastName());
        User savedUser = userService.update(user); // pass refreshed user to repository
        log.debug("trainee has been updated " + trainee.getId());
        return mapToDto(savedTrainee, savedUser); //mapping
    }

    @Override
    public boolean delete(TraineeDto traineeDto) throws EntityNotFoundException {
        Trainee trainee = traineeRepository.findById(traineeDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Could not found trainee with id " + traineeDto.getId()));
        log.debug("try delete trainee " + trainee.getId());
        return traineeRepository.delete(trainee);
    }


    private TraineeResponseDto mapToDto(Trainee trainee, User user) {
        return new TraineeResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                trainee.getDateOfBirth(),
                trainee.getAddress());
    }
}
