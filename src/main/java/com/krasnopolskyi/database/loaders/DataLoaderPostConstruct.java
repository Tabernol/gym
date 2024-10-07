package com.krasnopolskyi.database.loaders;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.database.StorageUtils;
import com.krasnopolskyi.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * this class loads data from external resource root/data/save/
 * only as different way for of configuration beans
 */
@Component
@Slf4j
public class DataLoaderPostConstruct {
    private final Storage storage;
    private final StorageUtils storageUtils;

    @Value("${data.save.users}")
    private String usersPath;
    @Value("${data.save.trainees}")
    private String traineesPath;
    @Value("${data.save.trainers}")
    private String trainersPath;
    @Value("${data.save.trainings}")
    private String trainingsPath;
    @Value("${data.save.training-types}")
    private String trainingTypesPath;

    public DataLoaderPostConstruct(Storage storage, StorageUtils storageUtils) {
        this.storage = storage;
        this.storageUtils = storageUtils;
    }

    @PostConstruct
    public void loadDataFromJson() {
        log.info("loading data using PostConstruct start");
        try {
            storage.getUsers().putAll(storageUtils.loadFromJsonFile(usersPath, Long.class, User.class));
            storage.getTrainingTypes().putAll(storageUtils.loadFromJsonFile(trainingTypesPath, Integer.class, TrainingType.class));
            storage.getTrainees().putAll(storageUtils.loadFromJsonFile(traineesPath, Long.class, Trainee.class));
            storage.getTrainers().putAll(storageUtils.loadFromJsonFile(trainersPath, Long.class, Trainer.class));
            storage.getTrainings().putAll(storageUtils.loadFromJsonFile(trainingsPath, Long.class, Training.class));
            log.info("Data upload successfully");
        } catch (IOException e) {
            log.error("Can't upload data from JSON file " + e.getMessage());
        }
        log.info("loading data using PostConstruct finish");
    }
}
