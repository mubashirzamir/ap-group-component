package com.kf7014.smartcitymicroservice.dto;

/**
 * Data Transfer Object for aggregated consumption data per provider.
 */
public class AggregatedDataByProviderDTO {
    private String providerId;
    private double totalConsumption;
    private double averageConsumption;

    public AggregatedDataByProviderDTO() {}

    public AggregatedDataByProviderDTO(String providerId, double totalConsumption, double averageConsumption) {
        this.providerId = providerId;
        this.totalConsumption = totalConsumption;
        this.averageConsumption = averageConsumption;
    }

    // Getters and Setters

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }
}
