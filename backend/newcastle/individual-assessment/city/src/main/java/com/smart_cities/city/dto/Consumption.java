package com.smart_cities.city.dto;

import java.time.LocalDateTime;

public class Consumption {
    private Long id;

    private Long entityId;

    private String entityType;

    private Long consumption;

    private LocalDateTime generatedAt;

    public Consumption(Long entityId, String entityType, Long consumption, LocalDateTime generatedAt) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.consumption = consumption;
        this.generatedAt = generatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getConsumption() {
        return this.consumption;
    }

    public void setConsumption(Long consumption) {
        this.consumption = consumption;
    }

    public LocalDateTime getGeneratedAt() {
        return this.generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
