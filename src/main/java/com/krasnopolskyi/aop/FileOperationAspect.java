package com.krasnopolskyi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Using AOP this class makes logs after each invocation
 * save() or delete() method in repositories
 */
@Aspect
@Component
@Slf4j
public class FileOperationAspect {
    // Pointcut for save/delete methods in all repository classes
    @After("execution(* com.krasnopolskyi.database.dao.*.save(..)) || " +
            "execution(* com.krasnopolskyi.database.dao.*.delete(..))")
    public void afterRepositoryOperation(JoinPoint joinPoint) {
        // Get the class name where the method was invoked
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Determine which repository was called and act accordingly
        switch (className) {
            case "TraineeRepository":
                log.info("TraineeRepository has done save/delete method");
                break;
            case "TrainerRepository":
                log.info("TrainerRepository has done save/delete method");
                break;
            case "UserRepository":
                log.info("UserRepository has done save/delete method");
                break;
            case "TrainingRepository":
                log.info("TrainingRepository has done save/delete method");
                break;
            case "TrainingTypeRepository":
                log.info("TrainingTypeRepository has done save/delete method");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + className);
        }
    }
}
