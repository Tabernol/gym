package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.exception.EntityException;
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
    public TraineeResponseDto save(TraineeDto traineeDto) throws EntityException {
        User user = userService.create(new UserDto(traineeDto.getFirstName(), traineeDto.getLastName()));

        Trainee trainee = new Trainee();
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setUser(user);
        Optional<Trainee> savedTrainee = traineeRepository.save(trainee);

//        log.info("trainee has been saved " + trainee.getId());
        return savedTrainee
                .map(this::mapToDto)
                .orElseThrow(() -> new EntityException("Failed to create trainee"));
    }

    @Override
    public Trainee findById(Long id) throws EntityException {
        return traineeRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found trainee with id " + id));

    }

    @Override
    public Trainee update(Trainee trainee) {
        return traineeRepository.save(trainee).get();
    }

    @Override
    public boolean delete(Trainee trainee) {
        log.info("attempt to delete trainee " + trainee.getId());
        return false;
    }


    private TraineeResponseDto mapToDto(Trainee trainee){
        return TraineeResponseDto.builder()
                .firstName(trainee.getUser().getFirstName())
                .lastName(trainee.getUser().getLastName())
                .username(trainee.getUser().getUsername())
                .password(trainee.getUser().getPassword())
                .address(trainee.getAddress())
                .dateOfBirth(trainee.getDateOfBirth())
                .build();
    }


}
