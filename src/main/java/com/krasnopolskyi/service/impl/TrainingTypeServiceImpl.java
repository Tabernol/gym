package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    @Autowired
    private TrainingTypeRepositoryImpl trainingTypeRepo;

    @Override
    public TrainingType findById(Integer id) throws EntityException {
        return trainingTypeRepo.findById(id)
                .orElseThrow(() -> new EntityException("Could not find training type with id " + id));
    }

}
