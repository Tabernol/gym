package com.krasnopolskyi.utils.mapper;

import com.krasnopolskyi.dto.request.TraineeDto;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.entity.Trainee;
import com.krasnopolskyi.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TraineeMapper {

    public static TraineeResponseDto mapToDto(Trainee trainee) {
        return new TraineeResponseDto(
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName(),
                trainee.getUser().getUsername(),
                trainee.getDateOfBirth(),
                trainee.getAddress());
    }

    public static Trainee mapToEntity(TraineeDto traineeDto, User user){
        Trainee trainee = new Trainee();
        trainee.setId(traineeDto.getId());
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setUser(user);
        return trainee;
    }
}
