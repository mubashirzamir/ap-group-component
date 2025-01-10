package com.kf7014.smartcitymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point for the Smart City Microservice application.
 * <p>
 * This class bootstraps the Spring Boot application, enabling auto-configuration,
 * component scanning, and scheduling support.
 * </p>
 * 
 * <p>
 * The {@code @EnableScheduling} annotation activates Spring's scheduled task execution capability,
 * allowing the application to run scheduled tasks defined within service classes.
 * </p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@SpringBootApplication
@EnableScheduling
public class SmartcitymicroserviceApplication {

	/**
	 * The main method that launches the Spring Boot application.
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(SmartcitymicroserviceApplication.class, args);
	}
}
