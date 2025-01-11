package com.kf7014.citizenmicroservice.model;

/**
 * Represents a meter reading in the Citizen Microservice.
 * 
 * <p>This class encapsulates the details of a meter's reading, including
 * whether it was generated automatically or submitted manually by a citizen.</p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
public class MeterReading {
    private String meterId;
    private double reading;
    private String timestamp;
    private boolean isManual; // Indicates if the reading is manual
    
    /**
     * Constructs a new {@code MeterReading} with the specified details.
     * 
     * @param meterId  The unique identifier of the meter.
     * @param reading  The meter reading value.
     * @param timestamp The timestamp when the reading was recorded.
     * @param isManual Indicates whether the reading was submitted manually.
     */
    public MeterReading(String meterId, double reading, String timestamp, boolean isManual) {
        this.setMeterId(meterId);
        this.setReading(reading);
        this.setTimestamp(timestamp);
        this.setManual(isManual);
    }

    /**
     * Retrieves the meter's unique identifier.
     * 
     * @return The {@code meterId} as a {@link String}.
     */
    public String getMeterId() {
        return meterId;
    }

    /**
     * Sets the meter's unique identifier.
     * 
     * @param meterId The {@code meterId} to set.
     */
    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    /**
     * Retrieves the meter reading value.
     * 
     * @return The {@code reading} as a {@code double}.
     */
    public double getReading() {
        return reading;
    }

    /**
     * Sets the meter reading value.
     * 
     * @param reading The {@code reading} to set.
     */
    public void setReading(double reading) {
        this.reading = reading;
    }

    /**
     * Retrieves the timestamp of the meter reading.
     * 
     * @return The {@code timestamp} as a {@link String}.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the meter reading.
     * 
     * @param timestamp The {@code timestamp} to set.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Indicates whether the meter reading was submitted manually.
     * 
     * @return {@code true} if the reading is manual; {@code false} otherwise.
     */
    public boolean isManual() {
        return isManual;
    }

    /**
     * Sets whether the meter reading was submitted manually.
     * 
     * @param isManual {@code true} if the reading is manual; {@code false} otherwise.
     */
    public void setManual(boolean isManual) {
        this.isManual = isManual;
    }
}
