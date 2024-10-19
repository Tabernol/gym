package com.krasnopolskyi.utils.mapper;

import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.Trainer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrainerMapper {

    public static TrainerResponseDto mapToDto(Trainer trainer) {
        return new TrainerResponseDto(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getUser().getUsername(),
                trainer.getSpecialization().getType());
    }
}
