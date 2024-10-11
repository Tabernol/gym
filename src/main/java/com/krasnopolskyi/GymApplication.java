package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.dto.response.TraineeResponseDto;
import com.krasnopolskyi.dto.response.TrainerResponseDto;
import com.krasnopolskyi.entity.*;
import com.krasnopolskyi.facade.MainFacade;
import com.krasnopolskyi.database.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        Storage storage = (Storage) context.getBean("storage");

        log.info("BEFORE trainingTypes size " + storage.getTrainingTypes().size());
        log.info("BEFORE users size " + storage.getUsers().size());
        log.info("BEFORE trainees size " + storage.getTrainees().size());
        log.info("BEFORE trainers size " + storage.getTrainers().size());
        log.info("BEFORE trainings size " + storage.getTrainings().size());

        MainFacade facade = context.getBean(MainFacade.class);

        log.info("=========================Creating trainee================================");
        facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.JOHN_TRAINEE);
        facade.createTrainee(Data.SHORT_NAME_TRAINEE);
        log.info("=========================Creating trainer================================");
        facade.createTrainer(Data.ARNI_TRAINER);
        facade.createTrainer(Data.TRAINER_NOT_VALID);
        log.info("==================== Creating training ========================");
        facade.addTraining(Data.TRAINING_VALID);
        facade.addTraining(Data.TRAINING_INVALID_TRAINEE);
        facade.addTraining(Data.TRAINING_INVALID_TRAINER);
        facade.addTraining(Data.TRAINING_INVALID_SPECIALIZATION);

        log.info("AFTER trainingTypes size " + storage.getTrainingTypes().size());
        log.info("AFTER users size " + storage.getUsers().size());
        log.info("AFTER trainees size " + storage.getTrainees().size());
        log.info("AFTER trainers size " + storage.getTrainers().size());
        log.info("AFTER trainings size " + storage.getTrainings().size());


    }
}
