package com.kf7014.electricityprovidermicroservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kf7014.electricityprovidermicroservice.model.MeterReading;
import com.kf7014.electricityprovidermicroservice.repository.MeterReadingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the Electricity Provider Microservice.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MeterReadingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeterReadingRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Integration test: Receive and retrieve a smart meter reading")
    public void testReceiveAndRetrieveMeterReading() throws Exception {
        // Arrange
        MeterReading reading = new MeterReading("SM01", 1234.56, Instant.now(), false);
        String json = objectMapper.writeValueAsString(reading);

        // Act: POST the reading
        mockMvc.perform(post("/api/readings/smartmeter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Meter reading received and stored."));

        // Act: GET all readings since a past timestamp
        String since = "2023-01-01T00:00:00Z";
        mockMvc.perform(get("/api/readings")
                .param("since", since))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].meterId").value("SM01"))
                .andExpect(jsonPath("$[0].reading").value(1234.56));
    }

    @Test
    @DisplayName("Integration test: Submit manual reading and retrieve latest")
    public void testSubmitManualReadingAndRetrieveLatest() throws Exception {
        // Arrange
        MeterReading reading1 = new MeterReading("SM01", 1000.0, Instant.parse("2023-08-10T10:00:00Z"), false);
        MeterReading reading2 = new MeterReading("SM01", 1500.0, Instant.parse("2023-08-20T10:00:00Z"), true);
        repository.save(reading1);
        repository.save(reading2);

        // Act: GET latest reading
        mockMvc.perform(get("/api/readings/meter/{meterId}/latest", "SM01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meterId").value("SM01"))
                .andExpect(jsonPath("$.reading").value(1500.0))
                .andExpect(jsonPath("$.manual").value(true));
    }

    @Test
    @DisplayName("Integration test: Retrieve meter readings by meter ID and timestamp")
    public void testRetrieveMeterReadingsByMeterIdAndTimestamp() throws Exception {
        // Arrange
        MeterReading reading1 = new MeterReading("SM01", 1000.0, Instant.parse("2023-08-10T10:00:00Z"), false);
        MeterReading reading2 = new MeterReading("SM01", 1500.0, Instant.parse("2023-08-20T10:00:00Z"), true);
        MeterReading reading3 = new MeterReading("SM02", 2000.0, Instant.parse("2023-08-15T10:00:00Z"), false);
        repository.save(reading1);
        repository.save(reading2);
        repository.save(reading3);

        // Act: GET readings for SM01 since 2023-08-15
        String since = "2023-08-15T00:00:00Z";
        mockMvc.perform(get("/api/readings/meter/{meterId}", "SM01")
                .param("since", since))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].meterId").value("SM01"))
                .andExpect(jsonPath("$[0].reading").value(1500.0));
    }

    @Test
    @DisplayName("Integration test: Handle invalid input during meter reading submission")
    public void testInvalidMeterReadingSubmission() throws Exception {
        // Arrange: Missing required fields
        MeterReading invalidReading = new MeterReading(null, -100.0, null, false);
        String json = objectMapper.writeValueAsString(invalidReading);

        // Act & Assert
        mockMvc.perform(post("/api/readings/smartmeter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Validation error(s):")));
    }

    @Test
    @DisplayName("Integration test: Retrieve latest reading for non-existent meter ID")
    public void testRetrieveLatestReadingNonExistentMeter() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/readings/meter/{meterId}/latest", "NON_EXISTENT"))
                .andExpect(status().isNoContent());
    }
}
