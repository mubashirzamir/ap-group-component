package com.smart_cities.citizen.service;

import com.smart_cities.citizen.model.Citizen;
import com.smart_cities.citizen.model.Meter;
import com.smart_cities.citizen.model.Reading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@Testcontainers
class ReadingServiceTest {

    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test")
            .withUsername("sa")
            .withPassword("sa");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    ReadingService readingService;

    @Test
    void testGenerateCitizenReading() {
        // Arrange
        Citizen citizen = new Citizen();
        citizen.setId(1L);
        citizen.setProviderId(1L);
        // Act
        Reading reading = this.readingService.generateReading(citizen);
        // Assert
        assertEquals(1L, reading.getEntityId());
        assertEquals("citizen", reading.getEntityType());
        assertTrue(reading.getConsumption() >= 0);
        assertNotNull(reading.getGeneratedAt());
    }

    @Test
    void testGenerateMeterReading() {
        // Arrange
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setProviderId(2L);
        // Act
        Reading reading = this.readingService.generateReading(meter);
        // Assert
        assertEquals(1L, reading.getEntityId());
        assertEquals("smartMeter", reading.getEntityType());
        assertTrue(reading.getConsumption() >= 0);
        assertNotNull(reading.getGeneratedAt());
    }
}