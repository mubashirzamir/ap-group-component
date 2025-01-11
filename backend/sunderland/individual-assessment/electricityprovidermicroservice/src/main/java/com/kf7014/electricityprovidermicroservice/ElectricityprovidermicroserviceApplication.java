package com.kf7014.electricityprovidermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Electricity Provider Microservice application.
 * <p>
 * This class bootstraps the Spring Boot application, enabling auto-configuration and component scanning.
 * </p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@SpringBootApplication
public class ElectricityprovidermicroserviceApplication {

    /**
     * The main method that launches the Spring Boot application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(ElectricityprovidermicroserviceApplication.class, args);
    }

}
