package com.krasnopolskyi.config;

import jakarta.annotation.PostConstruct;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;

@PropertySource("classpath:application.yaml")
@Configuration
@Slf4j
public class LiquibaseConfig {
    @Value("${hibernate.connection.url}")
    private String url;
    @Value("${hibernate.connection.username}")
    private String username;
    @Value("${hibernate.connection.password}")
    private String password;
    @Value("${datasource.master-change-log}")
    private String changeLogMaster;

    @PostConstruct
    public void runLiquibase() {
        try {
            Connection connection = DriverManager.getConnection(
                    url, username, password);
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            // Create a Liquibase instance
            Liquibase liquibase = new Liquibase(changeLogMaster, new ClassLoaderResourceAccessor(), database);

            // Run the update to apply migrations
            liquibase.update(new Contexts(), new LabelExpression());

            // Close the connection
            connection.close();
        } catch (Exception e) {
            log.warn("Liquibase migration failed");
            e.printStackTrace();
        }
    }
}
