package com.krasnopolskyi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;


@Configuration
@ComponentScan("com.krasnopolskyi")
@PropertySource("classpath:application.yaml")
@Slf4j
@EnableAspectJAutoProxy // enable AOP
public class AppConfiguration {

}
