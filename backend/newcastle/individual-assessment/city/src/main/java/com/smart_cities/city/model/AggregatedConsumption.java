package com.smart_cities.city.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aggregated_consumptions")
public class AggregatedConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long providerId;

    @Column(nullable = false)
    private Long totalConsumption;

    @Column(nullable = false)
    private Long averageConsumption;

    @Column(nullable = false)
    private LocalDateTime consumptionPeriodStart;

    @Column(nullable = false)
    private LocalDateTime consumptionPeriodEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getTotalConsumption() {
        return this.totalConsumption;
    }

    public void setTotalConsumption(Long totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public Long getAverageConsumption() {
        return this.averageConsumption;
    }

    public void setAverageConsumption(Long averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    public LocalDateTime getConsumptionPeriodStart() {
        return consumptionPeriodStart;
    }

    public void setConsumptionPeriodStart(LocalDateTime consumptionPeriodStart) {
        this.consumptionPeriodStart = consumptionPeriodStart;
    }

    public LocalDateTime getConsumptionPeriodEnd() {
        return consumptionPeriodEnd;
    }

    public void setConsumptionPeriodEnd(LocalDateTime consumptionPeriodEnd) {
        this.consumptionPeriodEnd = consumptionPeriodEnd;
    }
}
