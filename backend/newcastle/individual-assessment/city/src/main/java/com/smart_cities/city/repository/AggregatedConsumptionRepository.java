package com.smart_cities.city.repository;

import com.smart_cities.city.model.AggregatedConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatedConsumptionRepository extends JpaRepository<AggregatedConsumption, Long> {
}
