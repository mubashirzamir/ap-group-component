package com.smart_cities.city;

import org.springframework.boot.SpringApplication;

public class TestCityApplication {

	public static void main(String[] args) {
		SpringApplication.from(CityApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
