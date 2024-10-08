package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.dto.response.UserCredentials;
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
        UserCredentials john = facade.createTrainee(Data.JOHN_TRAINEE);
        UserCredentials john1 = facade.createTrainee(Data.JOHN_TRAINEE);
        UserCredentials john2 = facade.createTrainee(Data.JOHN_TRAINEE);

        log.info(john.toString());
        log.info(john1.toString());
        log.info(john2.toString());
        log.info("=========================Creating trainer================================");
        UserCredentials trainer = facade.createTrainer(Data.ARNI_TRAINER);
        UserCredentials failed = facade.createTrainer(Data.TRAINER_NOT_VALID);
        log.info(trainer.toString());
        log.info(failed.toString());
        log.info("==================== Creating training ========================");
        Training training = facade.addTraining(Data.trainingValid);
        Training training1 = facade.addTraining(Data.trainingInvalidTrainee);
        Training training2 = facade.addTraining(Data.trainingInvalidTrainer);
        Training training3 = facade.addTraining(Data.trainingInvalidSpecialization);
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
