package com.krasnopolskyi.aop;

import com.krasnopolskyi.database.Storage;
import com.krasnopolskyi.database.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FileOperationAspect {
    private final StorageUtils storageUtils;
    private final Storage storage;

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

    public FileOperationAspect(StorageUtils storageUtils, Storage storage) {
        this.storageUtils = storageUtils;
        this.storage = storage;
    }

    // Pointcut for save/delete methods in TraineeRepository class
    @After("execution(* com.krasnopolskyi.database.dao.TraineeRepository.save(..)), " +
            "execution(* com.krasnopolskyi.database.dao.TraineeRepository.delete(..))")
    public void afterSave() {
        log.info("AOP works");
        // Invoke the saving method after the repository save method
        storageUtils.saveToFile(trainees, storage.getTrainees());
    }
}
