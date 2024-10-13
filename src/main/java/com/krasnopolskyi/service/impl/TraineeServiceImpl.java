package com.krasnopolskyi.service.impl;


import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final UserService userService;

    @Override
    @Transactional
    public TraineeResponseDto save(TraineeDto traineeDto) throws ValidateException {
        User newUser = userService
                .create(new UserDto(traineeDto.getFirstName(),
                        traineeDto.getLastName())); //return user with firstName, lastName, username, password, isActive

        Trainee trainee = mapToEntity(traineeDto, newUser);

        Trainee savedTrainee = traineeRepository.save(trainee);// pass to repository
        log.debug("trainee has been saved " + trainee);
        return mapToDto(savedTrainee);
    }

    @Override
    public TraineeResponseDto findById(Long id) throws EntityException {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found trainee with id " + id)); // find trainee entity
//        User user = userService.findById(trainee.getUser().getId()); // find user associated with trainee
        return mapToDto(trainee); // mapping

    }

    @Override
    @Transactional
    public TraineeResponseDto findByUsername(String username) throws EntityException {
        return traineeRepository.findByUsername(username)
                .map(this::mapToDto)
                .orElseThrow(() -> new EntityException("Can't find trainee with username " + username));
    }

    @Override
    public TraineeResponseDto update(TraineeDto traineeDto) throws EntityException {
        // find trainee entity
        Trainee trainee = traineeRepository.findById(traineeDto.getId())
                .orElseThrow(() -> new EntityException("Could not found trainee with id " + traineeDto.getId()));
        //update trainee's fields
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
//        Trainee savedTrainee = traineeRepository.save(trainee); // pass trainee to repository

        User user = userService.findById(trainee.getUser().getId()); // find user associated with trainee
        //update user's fields
        user.setFirstName(traineeDto.getFirstName());
        user.setLastName(traineeDto.getLastName());
        User savedUser = userService.update(user); // pass refreshed user to repository
        log.debug("trainee has been updated " + trainee.getId());
//        return mapToDto(savedTrainee, savedUser); //mapping
        return null;
    }

    @Override
    @Transactional
    public boolean delete(String username) throws EntityException {
        return traineeRepository.delete(username);
//        return false;
    }


    private TraineeResponseDto mapToDto(Trainee trainee) {
        return new TraineeResponseDto(
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName(),
                trainee.getUser().getUsername(),
                trainee.getDateOfBirth(),
                trainee.getAddress());
    }

    private Trainee mapToEntity(TraineeDto traineeDto, User user){
        Trainee trainee = new Trainee();
        trainee.setId(traineeDto.getId());
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setUser(user);
        return trainee;
    }
}
