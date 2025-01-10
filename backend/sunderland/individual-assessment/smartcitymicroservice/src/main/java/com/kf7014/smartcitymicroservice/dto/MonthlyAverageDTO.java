package com.kf7014.smartcitymicroservice.dto;

/**
 * Data Transfer Object for monthly average consumption data.
 */
public class MonthlyAverageDTO {
    private String providerId; // Optional: null for city-wide averages
    private String month; // Format: YYYY-MM
    private double averageConsumption;

    public MonthlyAverageDTO() {}

    public MonthlyAverageDTO(String providerId, String month, double averageConsumption) {
        this.providerId = providerId;
        this.month = month;
        this.averageConsumption = averageConsumption;
    }

    // Getters and Setters

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }
}
