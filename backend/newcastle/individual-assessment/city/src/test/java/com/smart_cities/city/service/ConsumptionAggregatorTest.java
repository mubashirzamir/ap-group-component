package com.smart_cities.city.service;

import com.smart_cities.city.dto.Consumption;
import com.smart_cities.city.model.AggregatedConsumption;
import com.smart_cities.city.repository.AggregatedConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@Testcontainers
class ConsumptionAggregatorTest {
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
    private ConsumptionAggregator consumptionAggregator;

    @Autowired
    private AggregatedConsumptionRepository aggregatedConsumptionRepository;

    @Test
    void aggregate() {
        // Arrange
        Consumption consumption01 = new Consumption(1L, "citizen", 20L, LocalDateTime.of(2024, 1, 1, 0, 1, 30));
        Consumption consumption02 = new Consumption(2L, "smartMeter", 30L, LocalDateTime.of(2024, 1, 1, 0, 1, 30));
        Consumption consumption03 = new Consumption(1L, "citizen", 40L, LocalDateTime.of(2024, 1, 1, 0, 1, 45));
        Consumption consumption04 = new Consumption(2L, "smartMeter", 50L, LocalDateTime.of(2024, 1, 1, 0, 1, 45));

        List<Consumption> consumptions = List.of(consumption01, consumption02, consumption03, consumption04);
        // Act
        this.consumptionAggregator.aggregate(
                consumptions,
                1L,
                LocalDateTime.of(2024, 1, 1, 0, 1, 0),
                LocalDateTime.of(2024, 1, 1, 0, 2, 0)
        );
        // Assert
        AggregatedConsumption aggregatedConsumption = this.aggregatedConsumptionRepository.findAll().get(0);
        assert aggregatedConsumption.getProviderId() == 1L;
        assert aggregatedConsumption.getAverageConsumption() == 35L;
        assert aggregatedConsumption.getTotalConsumption() == 140L;
        assert aggregatedConsumption.getConsumptionPeriodStart().equals(LocalDateTime.of(2024, 1, 1, 0, 1, 0));
        assert aggregatedConsumption.getConsumptionPeriodEnd().equals(LocalDateTime.of(2024, 1, 1, 0, 2, 0));
    }
}