package com.krasnopolskyi.database;

import com.krasnopolskyi.entity.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This class imitates connection pool to database
 */
@Component
@Getter
public class Storage {
    private final Map<Integer, TrainingType> trainingTypes;
    private final Map<Long, User> users;
    private final Map<Long, Trainee> trainees;
    private final Map<Long, Trainer> trainers;
    private final Map<Long, Training> trainings;


    public Storage(
            Map<Integer, TrainingType> trainingTypes,
            Map<Long, User> users,
            Map<Long, Trainee> trainees,
            Map<Long, Trainer> trainers,
            Map<Long, Training> trainings) {
        this.trainingTypes = trainingTypes;
        this.users = users;
        this.trainees = trainees;
        this.trainers = trainers;
        this.trainings = trainings;
    }
}
