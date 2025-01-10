package com.kf7014.electricityprovidermicroservice.repository;

import com.kf7014.electricityprovidermicroservice.model.MeterReading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MeterReadingRepository}.
 */
@DataMongoTest
public class MeterReadingRepositoryTest {

    @Autowired
    private MeterReadingRepository repository;
    
    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Save and retrieve a meter reading")
    public void testSaveAndFindById() {
        // Arrange
        MeterReading reading = new MeterReading("SM01", 1234.56, Instant.now(), false);
        MeterReading savedReading = repository.save(reading);

        // Act
        MeterReading retrievedReading = repository.findById(savedReading.getId()).orElse(null);

        // Assert
        assertThat(retrievedReading).isNotNull();
        assertThat(retrievedReading.getMeterId()).isEqualTo("SM01");
        assertThat(retrievedReading.getReading()).isEqualTo(1234.56);
    }

    @Test
    @DisplayName("Find meter readings by meterId and timestamp greater than")
    public void testFindByMeterIdAndTimestampGreaterThan() {
        // Arrange
        Instant baseTime = Instant.parse("2023-08-01T00:00:00Z");
        MeterReading reading1 = new MeterReading("SM01", 1000.0, Instant.parse("2023-08-10T10:00:00Z"), false);
        MeterReading reading2 = new MeterReading("SM01", 1500.0, Instant.parse("2023-08-20T10:00:00Z"), false);
        MeterReading reading3 = new MeterReading("SM02", 2000.0, Instant.parse("2023-08-15T10:00:00Z"), false);
        repository.save(reading1);
        repository.save(reading2);
        repository.save(reading3);

        // Act
        List<MeterReading> readings = repository.findByMeterIdAndTimestampGreaterThan("SM01", baseTime);

        // Assert
        assertThat(readings).hasSize(2).extracting("meterId").containsOnly("SM01");
    }

    @Test
    @DisplayName("Find latest meter reading by meterId")
    public void testFindTopByMeterIdOrderByTimestampDesc() {
        // Arrange
        MeterReading reading1 = new MeterReading("SM01", 1000.0, Instant.parse("2023-08-10T10:00:00Z"), false);
        MeterReading reading2 = new MeterReading("SM01", 1500.0, Instant.parse("2023-08-20T10:00:00Z"), false);
        repository.save(reading1);
        repository.save(reading2);

        // Act
        MeterReading latestReading = repository.findTopByMeterIdOrderByTimestampDesc("SM01");

        // Assert
        assertThat(latestReading).isNotNull();
        assertThat(latestReading.getReading()).isEqualTo(1500.0);
    }

    @Test
    @DisplayName("Find all meter readings since a timestamp")
    public void testFindByTimestampGreaterThan() {
        // Arrange
        Instant baseTime = Instant.parse("2023-08-15T00:00:00Z");
        MeterReading reading1 = new MeterReading("SM01", 1000.0, Instant.parse("2023-08-10T10:00:00Z"), false);
        MeterReading reading2 = new MeterReading("SM02", 1500.0, Instant.parse("2023-08-20T10:00:00Z"), false);
        repository.save(reading1);
        repository.save(reading2);

        // Act
        List<MeterReading> readings = repository.findByTimestampGreaterThan(baseTime);

        // Assert
        assertThat(readings).hasSize(1);
        assertThat(readings.get(0).getMeterId()).isEqualTo("SM02");
    }
}
