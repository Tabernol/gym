package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.repository.TrainingTypeRepository;
import com.krasnopolskyi.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepo;

    @Override
    public TrainingType findById(Integer id) throws EntityException {
        return trainingTypeRepo.findById(id)
                .orElseThrow(() -> new EntityException("Could not find training type with id " + id));
    }

}
