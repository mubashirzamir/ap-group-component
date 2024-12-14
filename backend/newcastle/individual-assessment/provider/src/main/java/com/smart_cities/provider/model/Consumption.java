package com.smart_cities.provider.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumptions")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    @PositiveOrZero
    private int consumption;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    public Consumption() {

    }

    public Consumption(Long entityId, String entityType, int consumption, LocalDateTime generatedAt) {
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

    public int getConsumption() {
        return this.consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public LocalDateTime getGeneratedAt() {
        return this.generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
