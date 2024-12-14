package com.smart_cities.citizen.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "readings")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private Long consumption;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    public Reading() {

    }

    public Reading(Long entityId, String entityType, Long consumption, LocalDateTime generatedAt) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.consumption = consumption;
        this.generatedAt = generatedAt;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Map<String, Object> toPostPayload() {
        Map<String, Object> map = new HashMap<>();

        map.put("entityId", this.getEntityId());
        map.put("entityType", this.getEntityType());
        map.put("consumption", this.getConsumption());
        map.put("generatedAt", this.getGeneratedAt());

        return map;
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
