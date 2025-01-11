package com.kf7014.citizenmicroservice.model;

/**
 * Represents a utility meter in the Citizen Microservice.
 * 
 * <p>This class models both smart meters and citizen meters, tracking their
 * unique identifier and the last recorded reading.</p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
public class Meter {
    private String meterId;
    private double lastReading;

    /**
     * Constructs a new {@code Meter} with the specified identifier.
     * 
     * <p>The initial reading is set to {@code 0.0}.</p>
     * 
     * @param meterId The unique identifier for the meter.
     */
    public Meter(String meterId) {
        this.setMeterId(meterId);
        this.setLastReading(0.0); // Initial reading
    }

    /**
     * Generates a new reading that is higher than the last reading.
     * 
     * <p>The reading is increased by a random amount between {@code 0.1}
     * and {@code 5.0} units to simulate real-world usage.</p>
     * 
     * <p>This method is thread-safe to prevent concurrent modifications.</p>
     * 
     * @return The updated {@code lastReading} after increment.
     */
    public synchronized double generateReading() {
        // Increase the reading by a random amount between 0.1 and 5.0 units
        double increment = 0.1 + (Math.random() * 4.9);
        lastReading += increment;
        return lastReading;
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
     * Retrieves the last recorded reading of the meter.
     * 
     * @return The {@code lastReading} as a {@code double}.
     */
    public double getLastReading() {
        return lastReading;
    }
    
    /**
     * Sets the last recorded reading of the meter.
     * 
     * @param lastReading The {@code lastReading} to set.
     */
    public void setLastReading(double lastReading) {
        this.lastReading = lastReading;
    }
}
