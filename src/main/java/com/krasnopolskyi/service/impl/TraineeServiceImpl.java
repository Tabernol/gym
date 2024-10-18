package com.krasnopolskyi.service.impl;


import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.request.TrainerDto;
import com.krasnopolskyi.dto.request.UserDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.Trainer;
import com.krasnopolskyi.entity.User;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.ValidateException;
import com.krasnopolskyi.repository.TraineeRepository;
import com.krasnopolskyi.repository.TrainerRepository;
import com.krasnopolskyi.service.TraineeService;
import com.krasnopolskyi.service.UserService;
import com.krasnopolskyi.utils.mapper.TraineeMapper;
import com.krasnopolskyi.utils.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final UserService userService;

    @Override
    @Transactional
    public TraineeResponseDto save(TraineeDto traineeDto) throws ValidateException {
        User newUser = userService
                .create(new UserDto(traineeDto.getFirstName(),
                        traineeDto.getLastName())); //return user with firstName, lastName, username, password, isActive

        Trainee trainee = TraineeMapper.mapToEntity(traineeDto, newUser);

        Trainee savedTrainee = traineeRepository.save(trainee);// pass to repository
        log.debug("trainee has been saved " + trainee);
        return TraineeMapper.mapToDto(savedTrainee);
    }

    @Override
    @Transactional()
    public TraineeResponseDto findById(Long id) throws EntityException {
        return TraineeMapper.mapToDto(findTraineeById(id));

    }

    @Override
    @Transactional()
    public TraineeResponseDto findByUsername(String username) throws EntityException {
        return traineeRepository.findByUsername(username)
                .map(trainee -> TraineeMapper.mapToDto(trainee))
                .orElseThrow(() -> new EntityException("Can't find trainee with username " + username));
    }

    @Override
    @Transactional
    public TraineeResponseDto update(TraineeDto traineeDto) throws EntityException {
        // find trainee entity
        Trainee trainee = findTraineeById(traineeDto.getId());
        //update trainee's fields
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());

        //update user's fields
        User user = userService.findById(trainee.getUser().getId()); // find user associated with trainee
        user.setFirstName(traineeDto.getFirstName());
        user.setLastName(traineeDto.getLastName());

        Trainee savedTrainee = traineeRepository.save(trainee);
        log.debug("trainee has been updated " + trainee.getId());
        return TraineeMapper.mapToDto(savedTrainee);
    }

    @Override
    @Transactional
    public boolean delete(String username) throws EntityException {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new EntityException("Can't find trainee with username " + username));
        return traineeRepository.delete(trainee);
    }

    @Override
    @Transactional
    public List<TrainerResponseDto> updateTrainers(TraineeDto traineeDto, List<TrainerDto> trainerDtoList) throws EntityException {
        Trainee trainee = findTraineeById(traineeDto.getId());
        trainee.getTrainers().clear();
        for (TrainerDto trainerDto : trainerDtoList) {
            Trainer trainer = trainerRepository.findById(trainerDto.getId())
                    .orElseThrow(() -> new EntityException("Could not found trainer with id " + trainerDto.getId()));
            trainer.getTrainees().add(trainee); // save to set and to database
            trainee.getTrainers().add(trainer);
        }

        return trainee.getTrainers()
                .stream()
                .map(TrainerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TrainerResponseDto> findAllNotAssignedTrainersByTrainee(String username) throws EntityException {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new EntityException("Can't find trainee with username " + username));

        List<Trainer> allTrainers = trainerRepository.findAll();
        allTrainers.removeAll(trainee.getTrainers()); // todo check this
        return allTrainers.stream().map(TrainerMapper::mapToDto).toList();
    }


    private Trainee findTraineeById(Long id) throws EntityException {
        return traineeRepository.findById(id)
                .orElseThrow(() -> new EntityException("Could not found trainee with id " + id)); // find trainee entity
    }

}
