package com.smart_cities.provider.repository;

import com.smart_cities.provider.model.Consumption;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@Testcontainers
class ConsumptionRepositoryTest {

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
    private ConsumptionRepository consumptionRepository;

    @Test
    void testFindAllConsumptions() {
        // Arrange
        Consumption consumption01 = new Consumption(1L, "citizen", 5, LocalDateTime.of(2024, 10, 1, 12, 1));
        Consumption consumption02 = new Consumption(2L, "citizen", 10, LocalDateTime.of(2024, 10, 1, 12, 2));
        this.consumptionRepository.saveAll(List.of(consumption01, consumption02));
        // Act
        List<Consumption> consumptions = this.consumptionRepository.findAll();
        // Assert
        assert consumptions.size() == 2;

        assert consumptions.get(0).getEntityId().equals(1L);
        assert consumptions.get(0).getEntityType().equals("citizen");
        assert consumptions.get(0).getConsumption() == 5;
        assert consumptions.get(0).getGeneratedAt().equals(LocalDateTime.of(2024, 10, 1, 12, 1));

        assert consumptions.get(1).getEntityId().equals(2L);
        assert consumptions.get(1).getEntityType().equals("citizen");
        assert consumptions.get(1).getConsumption() == 10;
        assert consumptions.get(1).getGeneratedAt().equals(LocalDateTime.of(2024, 10, 1, 12, 2));
    }
}