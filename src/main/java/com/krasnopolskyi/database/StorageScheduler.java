package com.krasnopolskyi.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class StorageScheduler {
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

    public StorageScheduler(Storage storage, StorageUtils storageUtils) {
        this.storage = storage;
        this.storageUtils = storageUtils;
    }

    // This method will run every 5 seconds
    public void saveDataToJson() {
        log.info("Scheduler runs -----------------------");
        try {
            storageUtils.saveToJsonFile(storage.getUsers(), users);
            storageUtils.saveToJsonFile(storage.getTrainingTypes(), trainingTypes);
            storageUtils.saveToJsonFile(storage.getTrainees(), trainees);
            storageUtils.saveToJsonFile(storage.getTrainers(), trainers);
            storageUtils.saveToJsonFile(storage.getTrainings(), trainings);
        } catch (IOException e) {
            log.error("could not save data to JSON file", e);
        }
    }
}
