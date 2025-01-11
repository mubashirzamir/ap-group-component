package com.kf7014.smartcitymicroservice.dto;

/**
 * Data Transfer Object for aggregated consumption data for the entire city.
 */
public class AggregatedDataForCityDTO {
    private double totalConsumption;
    private double averageConsumption;

    public AggregatedDataForCityDTO() {}

    public AggregatedDataForCityDTO(double totalConsumption, double averageConsumption) {
        this.totalConsumption = totalConsumption;
        this.averageConsumption = averageConsumption;
    }

    // Getters and Setters

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
