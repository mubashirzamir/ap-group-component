package com.smart_cities.citizen.model;

import com.smart_cities.citizen.contracts.IsAbleToCreateReadings;
import jakarta.persistence.*;

@Entity
@Table(name = "meters")
public class Meter implements IsAbleToCreateReadings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Long providerId;

    public Meter() {
    }

    public Meter(Long providerId) {
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProviderId() {
        return this.providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getType() {
        return "smartMeter";
    }
}
