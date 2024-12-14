package com.smart_cities.provider;

import org.springframework.boot.SpringApplication;

public class TestProviderApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProviderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
