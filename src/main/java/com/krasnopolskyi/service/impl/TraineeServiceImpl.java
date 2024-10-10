package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.UserCredentials;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;

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
        return traineeRepository.save(trainee).get();
    }

    @Override
    public boolean delete(Trainee trainee) {
        log.info("attempt to delete trainee " + trainee.getId());
        return false;
    }


}
