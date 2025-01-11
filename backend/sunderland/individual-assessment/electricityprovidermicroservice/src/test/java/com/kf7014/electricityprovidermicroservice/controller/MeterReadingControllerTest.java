package com.kf7014.electricityprovidermicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kf7014.electricityprovidermicroservice.model.MeterReading;
import com.kf7014.electricityprovidermicroservice.service.MeterReadingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link MeterReadingController}.
 */
@WebMvcTest(MeterReadingController.class)
public class MeterReadingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeterReadingService meterReadingService;

    @Autowired
    private ObjectMapper objectMapper;

    private MeterReading sampleReading;

    @BeforeEach
    public void setup() {
        sampleReading = new MeterReading("SM01", 1234.56, Instant.parse("2023-08-15T10:15:30Z"), false);
    }

    @Test
    public void testReceiveMeterReading_Success() throws Exception {
        // Arrange
        String json = objectMapper.writeValueAsString(sampleReading);

        // Act & Assert
        mockMvc.perform(post("/api/readings/smartmeter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Meter reading received and stored."));
    }

    @Test
    public void testReceiveMeterReading_InvalidInput() throws Exception {
        // Arrange: Missing meterId
        MeterReading invalidReading = new MeterReading(null, -100.0, null, false);
        String json = objectMapper.writeValueAsString(invalidReading);

        // Act & Assert
        mockMvc.perform(post("/api/readings/smartmeter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Validation error(s):")));
    }

    @Test
    public void testSubmitManualReading_Success() throws Exception {
        // Arrange
        sampleReading.setManual(true);
        String json = objectMapper.writeValueAsString(sampleReading);

        // Act & Assert
        mockMvc.perform(post("/api/readings/manual")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Manual meter reading received and stored."));
    }

    @Test
    public void testSubmitManualReading_ServiceException() throws Exception {
        // Arrange
        String json = objectMapper.writeValueAsString(sampleReading);
        doThrow(new RuntimeException("Service error")).when(meterReadingService).saveManualMeterReading(any(MeterReading.class));

        // Act & Assert
        mockMvc.perform(post("/api/readings/manual")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error processing manual meter reading: Service error")));
    }

    @Test
    public void testGetAllMeterReadings_Success() throws Exception {
        // Arrange
        String since = "2023-08-01T00:00:00Z";
        List<MeterReading> readings = Arrays.asList(sampleReading);
        when(meterReadingService.getAllMeterReadingsSince(any(Instant.class))).thenReturn(readings);

        // Act & Assert
        mockMvc.perform(get("/api/readings")
                .param("since", since))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].meterId").value("SM01"));
    }

    @Test
    public void testGetAllMeterReadings_NoContent() throws Exception {
        // Arrange
        String since = "2023-08-01T00:00:00Z";
        when(meterReadingService.getAllMeterReadingsSince(any(Instant.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/readings")
                .param("since", since))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllMeterReadings_InvalidSince() throws Exception {
        // Arrange
        String invalidSince = "invalid-timestamp";

        // Act & Assert
        mockMvc.perform(get("/api/readings")
                .param("since", invalidSince))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetMeterReadingsByMeterId_Success() throws Exception {
        // Arrange
        String meterId = "SM01";
        String since = "2023-08-01T00:00:00Z";
        List<MeterReading> readings = Arrays.asList(sampleReading);
        when(meterReadingService.getMeterReadingsByMeterIdSince(eq(meterId), any(Instant.class))).thenReturn(readings);

        // Act & Assert
        mockMvc.perform(get("/api/readings/meter/{meterId}", meterId)
                .param("since", since))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].meterId").value("SM01"));
    }

    @Test
    public void testGetMeterReadingsByMeterId_NoContent() throws Exception {
        // Arrange
        String meterId = "SM01";
        String since = "2023-08-01T00:00:00Z";
        when(meterReadingService.getMeterReadingsByMeterIdSince(eq(meterId), any(Instant.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/readings/meter/{meterId}", meterId)
                .param("since", since))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetMeterReadingsByMeterId_InvalidSince() throws Exception {
        // Arrange
        String meterId = "SM01";
        String invalidSince = "invalid-timestamp";

        // Act & Assert
        mockMvc.perform(get("/api/readings/meter/{meterId}", meterId)
                .param("since", invalidSince))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetLatestMeterReadingByMeterId_Success() throws Exception {
        // Arrange
        String meterId = "SM01";
        when(meterReadingService.getLatestMeterReadingByMeterId(eq(meterId))).thenReturn(sampleReading);

        // Act & Assert
        mockMvc.perform(get("/api/readings/meter/{meterId}/latest", meterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meterId").value("SM01"));
    }

    @Test
    public void testGetLatestMeterReadingByMeterId_NoContent() throws Exception {
        // Arrange
        String meterId = "SM01";
        when(meterReadingService.getLatestMeterReadingByMeterId(eq(meterId))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/readings/meter/{meterId}/latest", meterId))
                .andExpect(status().isNoContent());
    }
}
