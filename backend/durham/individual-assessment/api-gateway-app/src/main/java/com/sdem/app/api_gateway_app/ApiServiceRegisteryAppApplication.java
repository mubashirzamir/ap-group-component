package com.sdem.app.api_gateway_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApiServiceRegisteryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServiceRegisteryAppApplication.class, args);
	}

}
