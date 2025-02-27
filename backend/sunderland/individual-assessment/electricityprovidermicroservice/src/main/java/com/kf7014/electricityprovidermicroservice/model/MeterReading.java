package com.kf7014.electricityprovidermicroservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Represents a meter reading in the Electricity Provider Microservice.
 * <p>
 * This model maps to the {@code meter_readings} collection in MongoDB and includes details such as
 * meter ID, reading value, timestamp, and whether the reading was submitted manually.
 * </p>
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Document(collection = "meter_readings")
@Schema(description = "Meter Reading Entity representing a single meter reading.")
public class MeterReading {

    /**
     * The unique identifier for the meter reading.
     * <p>
     * This field is auto-generated by MongoDB.
     * </p>
     */
    @Id
    @Schema(description = "Unique identifier for the meter reading.", example = "60d5f483f8d4e2a4c8d6f8b1", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    /**
     * The unique identifier of the meter.
     */
    @NotNull(message = "Meter ID cannot be null")
    @Schema(description = "Unique identifier of the meter.", example = "SM01")
    private String meterId;

    /**
     * The reading value from the meter.
     */
    @Positive(message = "Reading must be positive")
    @NotNull(message = "Meter Reading cannot be null")
    @Schema(description = "Reading value from the meter.", example = "1234.56")
    private Double reading;

    /**
     * The timestamp when the reading was recorded.
     */
    @NotNull(message = "Timestamp cannot be null")
    @Schema(description = "Timestamp when the reading was recorded in ISO-8601 format.", example = "2023-08-15T10:15:30Z")
    private Instant timestamp;

    /**
     * Indicates whether the reading was submitted manually.
     */
    @NotNull(message = "Manual cannot be null")
    @Schema(description = "Indicates if the reading was submitted manually.", example = "false")
    private Boolean isManual;


    /**
     * Constructs a new {@code MeterReading} with the specified details.
     *
     * @param meterId   the unique identifier of the meter
     * @param reading   the reading value
     * @param timestamp the timestamp of the reading
     * @param isManual  indicates if the reading was submitted manually
     */
    public MeterReading(String meterId, double reading, Instant timestamp, boolean isManual) {
        this.setMeterId(meterId);
        this.setReading(reading);
        this.setTimestamp(timestamp);
        this.setManual(isManual);
    }

    /**
     * Retrieves the unique identifier of the meter reading.
     *
     * @return the unique identifier of the meter reading
     */
    public String getId() {
        return id;
    }

    // No setter for ID, as it's auto-generated by MongoDB

    /**
     * Retrieves the meter ID associated with this reading.
     *
     * @return the meter ID
     */
    public String getMeterId() {
        return meterId;
    }

    /**
     * Sets the meter ID for this reading.
     *
     * @param meterId the meter ID to set
     */
    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    /**
     * Retrieves the reading value.
     *
     * @return the reading value
     */
    public double getReading() {
        return reading;
    }

    /**
     * Sets the reading value.
     *
     * @param reading the reading value to set
     */
    public void setReading(double reading) {
        this.reading = reading;
    }

    /**
     * Retrieves the timestamp of the reading.
     *
     * @return the timestamp of the reading
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the reading.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Indicates whether the reading was submitted manually.
     *
     * @return {@code true} if the reading was manual; {@code false} otherwise
     */
    public boolean isManual() {
        return isManual;
    }

    /**
     * Sets the manual flag for the reading.
     *
     * @param isManual {@code true} if the reading is manual; {@code false} otherwise
     */
    public void setManual(boolean isManual) {
        this.isManual = isManual;
    }
}
