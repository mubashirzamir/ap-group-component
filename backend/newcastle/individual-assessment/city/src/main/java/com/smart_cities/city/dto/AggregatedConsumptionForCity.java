package com.smart_cities.city.dto;

public class AggregatedConsumptionForCity {
    private Long totalConsumption;
    private Double averageConsumption;

    public AggregatedConsumptionForCity(Long totalConsumption, Double averageConsumption) {
        this.totalConsumption = totalConsumption;
        this.averageConsumption = averageConsumption;
    }

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(Double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    public Long getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Long totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
