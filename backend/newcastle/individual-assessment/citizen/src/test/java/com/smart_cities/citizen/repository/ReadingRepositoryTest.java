package com.smart_cities.citizen.repository;

import com.smart_cities.citizen.model.Reading;
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
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@Testcontainers
class ReadingRepositoryTest {
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
    ReadingRepository readingRepository;

    @Test
    void findByEntityIdAndEntityType() {
        // Arrange
        Reading reading = new Reading(1L, "citizen", 5L, LocalDateTime.of(2024, 10, 1, 12, 1));
        this.readingRepository.save(reading);
        // Act
        Optional<Reading> result = this.readingRepository.findByEntityIdAndEntityType(1L, "citizen");
        // Assert
        assert result.isPresent();
        assert result.get().getEntityId().equals(1L);
        assert result.get().getEntityType().equals("citizen");
        assert result.get().getConsumption().equals(5L);
        assert result.get().getGeneratedAt().equals(LocalDateTime.of(2024, 10, 1, 12, 1));
    }
}