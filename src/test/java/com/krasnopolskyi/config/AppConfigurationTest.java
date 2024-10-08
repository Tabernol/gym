package com.krasnopolskyi.config;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigurationTest {

    @Test
    void testBeansInitialization() {
        // Load the AppConfiguration class into the Spring context
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class)) {
            // Test trainingTypesMap bean
            Map<Integer, TrainingType> trainingTypesMap = context.getBean("trainingTypesMap", Map.class);
            assertNotNull(trainingTypesMap, "Training types map should not be null");

            // Test usersMap bean
            Map<Long, User> usersMap = context.getBean("usersMap", Map.class);
            assertNotNull(usersMap, "Users map should not be null");

            // Test traineesMap bean
            Map<Long, Trainee> traineesMap = context.getBean("traineesMap", Map.class);
            assertNotNull(traineesMap, "Trainees map should not be null");

            // Test trainersMap bean
            Map<Long, Trainer> trainersMap = context.getBean("trainersMap", Map.class);
            assertNotNull(trainersMap, "Trainers map should not be null");

            // Test trainingsMap bean
            Map<Long, Training> trainingsMap = context.getBean("trainingsMap", Map.class);
            assertNotNull(trainingsMap, "Trainings map should not be null");

            // Test storage bean
            Storage storage = context.getBean(Storage.class);
            assertNotNull(storage, "Storage should not be null");
            assertEquals(trainingTypesMap, storage.getTrainingTypes(), "Storage training types should match");
            assertEquals(usersMap, storage.getUsers(), "Storage users should match");
            assertEquals(traineesMap, storage.getTrainees(), "Storage trainees should match");
            assertEquals(trainersMap, storage.getTrainers(), "Storage trainers should match");
            assertEquals(trainingsMap, storage.getTrainings(), "Storage trainings should match");
        }
    }
}
