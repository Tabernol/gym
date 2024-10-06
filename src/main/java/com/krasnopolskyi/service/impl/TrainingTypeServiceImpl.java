package com.krasnopolskyi.service.impl;

import com.krasnopolskyi.database.dao.TrainingTypeRepository;
import com.krasnopolskyi.entity.TrainingType;
import com.krasnopolskyi.exception.EntityNotFoundException;
import com.krasnopolskyi.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    @Autowired
    private TrainingTypeRepository trainingTypeRepo;

    @Override
    public TrainingType findById(Integer id) throws EntityNotFoundException {
        return trainingTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find training type with id " + id));
    }

}
