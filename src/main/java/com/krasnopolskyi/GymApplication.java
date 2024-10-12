package com.krasnopolskyi;

import com.krasnopolskyi.config.AppConfiguration;
import com.krasnopolskyi.facade.MainFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class GymApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MainFacade facade = context.getBean(MainFacade.class);

        System.out.println("---------------------=============-----------");
        log.warn("========================== GYM ==============================");

//        facade.createTrainee(Data.JOHN_TRAINEE);


    }
}
