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
        TraineeResponseDto trainee = facade.createTrainee(Data.JOHN_TRAINEE);
        TraineeResponseDto trainee1 = facade.createTrainee(Data.JOHN_TRAINEE);
        TraineeResponseDto trainee2 = facade.createTrainee(Data.JOHN_TRAINEE);

        log.info(trainee.toString());
        log.info(trainee1.toString());
        log.info(trainee2.toString());
        log.info("=========================Creating trainer================================");
        TrainerResponseDto trainer = facade.createTrainer(Data.ARNI_TRAINER);
        TrainerResponseDto failed = facade.createTrainer(Data.TRAINER_NOT_VALID);
        log.info(trainer.toString());
        log.info("is null " + (failed == null));
        log.info("==================== Creating training ========================");
        Training training = facade.addTraining(Data.TRAINING_VALID);
        Training training1 = facade.addTraining(Data.TRAINING_INVALID_TRAINEE);
        Training training2 = facade.addTraining(Data.TRAINING_INVALID_TRAINER);
        Training training3 = facade.addTraining(Data.TRAINING_INVALID_SPECIALIZATION);
        log.info(training.toString());
        log.info("training1 " + training1);
        log.info("training2 " + training2);
        log.info("training3 " + training3);

        log.info("AFTER trainingTypes size " + storage.getTrainingTypes().size());
        log.info("AFTER users size " + storage.getUsers().size());
        log.info("AFTER trainees size " + storage.getTrainees().size());
        log.info("AFTER trainers size " + storage.getTrainers().size());
        log.info("AFTER trainings size " + storage.getTrainings().size());


    }
}
