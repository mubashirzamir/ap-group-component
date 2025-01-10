package com.kf7014.smartcitymicroservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a meter reading from a provider.
 */
@Document(collection = "meter_readings")
public class MeterReading {
    
    @Id
    private String id;
    private String providerId;
    private Date timestamp;
    private double reading;

    // Constructors
    public MeterReading() {}

    public MeterReading(String providerId, Date timestamp, double reading) {
        this.providerId = providerId;
        this.timestamp = timestamp;
        this.reading = reading;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }
}
