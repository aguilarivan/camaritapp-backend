package com.eventapp.photoevent.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class DatabaseConfig {

    @Bean
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection();
    }

    @Bean
    public ExampleObjectRepository exampleObjectRepository(DatabaseConnection dbConn) {
        Connection connection = dbConn.getConnection();
        return new ExampleObjectRepository(connection);
    }
}
