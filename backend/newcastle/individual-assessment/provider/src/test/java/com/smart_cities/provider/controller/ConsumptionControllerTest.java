package com.smart_cities.provider.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_cities.provider.model.Consumption;
import com.smart_cities.provider.repository.ConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@Testcontainers
class ConsumptionControllerTest {

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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Test
    void getAllConsumptions() throws Exception {
        // Arrange
        Consumption consumption01 = new Consumption(1L, "citizen", 5, LocalDateTime.of(2024, 10, 1, 12, 1));
        Consumption consumption02 = new Consumption(2L, "meter", 7, LocalDateTime.of(2024, 10, 1, 12, 2));
        Consumption consumption03 = new Consumption(3L, "citizen", 9, LocalDateTime.of(2024, 10, 1, 12, 3));
        Consumption consumption04 = new Consumption(4L, "meter", 11, LocalDateTime.of(2024, 10, 1, 12, 4));

        this.consumptionRepository.save(consumption01);
        this.consumptionRepository.save(consumption02);
        this.consumptionRepository.save(consumption03);
        this.consumptionRepository.save(consumption04);
        // Act
        ResultActions resultActions = mockMvc.perform(get("/consumptions"));
        // Assert
        String expectedResponse = """
                [
                    {
                        "id": 1,
                        "entityId": 1,
                        "entityType": "citizen",
                        "consumption": 5,
                        "generatedAt": "2024-10-01T12:01:00"
                    },
                    {
                        "id": 2,
                        "entityId": 2,
                        "entityType": "meter",
                        "consumption": 7,
                        "generatedAt": "2024-10-01T12:02:00"
                    },
                    {
                        "id": 3,
                        "entityId": 3,
                        "entityType": "citizen",
                        "consumption": 9,
                        "generatedAt": "2024-10-01T12:03:00"
                    },
                    {
                        "id": 4,
                        "entityId": 4,
                        "entityType": "meter",
                        "consumption": 11,
                        "generatedAt": "2024-10-01T12:04:00"
                    }
                ]
                """;
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void getAllConsumptionsFiltered() throws Exception {
        // Arrange
        Consumption consumption01 = new Consumption(1L, "citizen", 5, LocalDateTime.of(2024, 11, 1, 12, 1));
        Consumption consumption02 = new Consumption(2L, "meter", 7, LocalDateTime.of(2024, 11, 1, 12, 2));
        Consumption consumption03 = new Consumption(3L, "citizen", 9, LocalDateTime.of(2024, 11, 1, 12, 3));
        Consumption consumption04 = new Consumption(4L, "meter", 11, LocalDateTime.of(2024, 11, 1, 12, 4));

        this.consumptionRepository.save(consumption01);
        this.consumptionRepository.save(consumption02);
        this.consumptionRepository.save(consumption03);
        this.consumptionRepository.save(consumption04);

        // Act
        ResultActions resultActions = mockMvc.perform(get(
                "/consumptions?consumptionPeriodStart=2024-11-01T12:01:59&consumptionPeriodEnd=2024-11-01T12:03:01"));
        // Assert
        String expectedResponse = """
                [
                    {
                        "id": 6,
                        "entityId": 2,
                        "entityType": "meter",
                        "consumption": 7,
                        "generatedAt": "2024-11-01T12:02:00"
                    },
                    {
                        "id": 7,
                        "entityId": 3,
                        "entityType": "citizen",
                        "consumption": 9,
                        "generatedAt": "2024-11-01T12:03:00"
                    }
                ]
                """;
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void createConsumption() throws Exception {
        // Arrange
        Consumption consumption = new Consumption(1L, "citizen", 13, LocalDateTime.of(2024, 10, 1, 12, 5));
        // Act
        ResultActions resultActions = mockMvc.perform(post("/consumptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consumption)));
        // Assert
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.entityId").value(consumption.getEntityId()))
                .andExpect(jsonPath("$.entityType").value(consumption.getEntityType()))
                .andExpect(jsonPath("$.consumption").value(consumption.getConsumption()))
                .andExpect(jsonPath("$.generatedAt").value(consumption.getGeneratedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }
}