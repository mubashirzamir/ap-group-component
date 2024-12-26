package com.smart_cities.city.dto;

public class MonthlyAverageForCity {
    private Integer month;
    private Double averageConsumption;

    public MonthlyAverageForCity(Integer month, Double averageConsumption) {
        this.month = month;
        this.averageConsumption = averageConsumption;
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

