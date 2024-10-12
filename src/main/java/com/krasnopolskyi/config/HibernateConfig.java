package com.krasnopolskyi.config;

import com.krasnopolskyi.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.yaml")
@Slf4j
public class HibernateConfig {
    @Value("${hibernate.connection.url}")
    private String url;
    @Value("${hibernate.connection.driver}")
    private String driver;
    @Value("${hibernate.connection.username}")
    private String username;
    @Value("${hibernate.connection.password}")
    private String password;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.current_session_context_class}")
    private String context; // threat
    @Value("${hibernate.show_sql}")
    private boolean showSql;
    @Value("${hibernate.format_sql}")
    private boolean formatSql;

    @Bean(name = "hibernateProperties")
    public Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", url);
        properties.put("hibernate.connection.driver_class", driver);
        properties.put("hibernate.connection.username", username);
        properties.put("hibernate.connection.password", password);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        properties.put("hibernate.current_session_context_class", context);
        return properties;
    }

    @Bean
    public SessionFactory sessionFactory(@Qualifier("hibernateProperties") Properties prop) {
        return new org.hibernate.cfg.Configuration()
                .addProperties(prop) // general properties
                .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy()) // converts camelCase in Java to underScore in SQL


                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Trainee.class)
                .addAnnotatedClass(Trainer.class)
                .addAnnotatedClass(Training.class)
                .addAnnotatedClass(TrainingType.class)

                .buildSessionFactory();
    }
}
