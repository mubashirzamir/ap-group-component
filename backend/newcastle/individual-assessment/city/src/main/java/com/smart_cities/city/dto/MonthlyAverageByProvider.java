package com.smart_cities.city.dto;

public class MonthlyAverageByProvider {
    private Long providerId;
    private Integer month;
    private Double averageConsumption;

    public MonthlyAverageByProvider(Long providerId, Integer month, Double averageConsumption) {
        this.providerId = providerId;
        this.month = month;
        this.averageConsumption = averageConsumption;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(Double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }
}

