package com.kf7014.citizenmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point for the Citizen Microservice application.
 * 
 * <p>This class bootstraps the Spring Boot application and enables scheduling
 * for periodic tasks such as generating and sending meter readings.</p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@SpringBootApplication
@EnableScheduling
public class CitizenmicroserviceApplication {

    /**
     * The main method that launches the Spring Boot application.
     * 
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(CitizenmicroserviceApplication.class, args);
    }
    
}
