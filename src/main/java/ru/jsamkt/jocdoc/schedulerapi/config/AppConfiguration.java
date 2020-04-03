package ru.jsamkt.jocdoc.schedulerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
public class AppConfiguration {

    @Bean
    MongoTransactionManager txManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}