package com.krasnopolskyi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
@PropertySource("classpath:application.yaml")
@Configuration
public class DataSourceConfig {
    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.pool-size}")
    private int poolSize;

    @Value("${datasource.master-change-log}")
    private String changeLogMaster;

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(poolSize);
        return new HikariDataSource(config);
    }

//    @Bean
//    public SpringLiquibase liquibase() {
//        System.out.println(changeLogMaster);
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog(changeLogMaster);
//        liquibase.setDataSource(dataSource());
//        liquibase.setDefaultSchema("public");
//        return liquibase;
//    }
}
