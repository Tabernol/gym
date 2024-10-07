package com.krasnopolskyi.database.loaders;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.database.StorageUtils;
import com.krasnopolskyi.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Slf4j
public class DataLoaderPostConstruct {
    private final Storage storage;
    private final StorageUtils storageUtils;

    @Value("${data.save.users}")
    private String users;
    @Value("${data.save.trainees}")
    private String trainees;
    @Value("${data.save.trainers}")
    private String trainers;
    @Value("${data.save.trainings}")
    private String trainings;
    @Value("${data.save.training-types}")
    private String trainingTypes;

    public DataLoaderPostConstruct(Storage storage, StorageUtils storageUtils) {
        this.storage = storage;
        this.storageUtils = storageUtils;
    }

    @PostConstruct
    public void loadDataFromJson() {
        log.info("load data using PostConstruct");
        log.info("---------------------------------------------------------------------");
        try {
//            storage.getUsers().putAll(storageUtils.loadFromJsonFile(users, Long.class, User.class));
//            storage.getTrainingTypes().putAll(storageUtils.loadFromJsonFile(trainingTypes, Integer.class, TrainingType.class));
            storage.getTrainees().putAll(storageUtils.loadFromJsonFile(trainees, Long.class, Trainee.class));
//            storage.getTrainers().putAll(storageUtils.loadFromJsonFile(trainers, Long.class, Trainer.class));
//            storage.getTrainings().putAll(storageUtils.loadFromJsonFile(trainings, Long.class, Training.class));
        } catch (IOException e) {
            log.error("Can't upload data from JSON file", e);
        }
    }
}
