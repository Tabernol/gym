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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    // initialized via autowired because task condition 4
    // I prefer initialized via constructor
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private UserService userService;

    @Override
    public UserCredentials save(TraineeDto traineeDto) {
//        User user = userService.save(new UserDto(traineeDto.getFirstName(), traineeDto.getLastName()));
//
//        traineeRepository.save(trainee);
//        log.info("trainee has been saved " + trainee.getId());
        return new UserCredentials("", "");
    }

    @Override
    public Trainee findById(Long id) throws EntityNotFoundException {
        return traineeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not found trainee with id " + id));

    }

    @Override
    public Trainee update(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    @Override
    public boolean delete(Trainee trainee) {
        log.info("attempt to delete trainee " + trainee.getId());
        return traineeRepository.delete(trainee);
    }


}
