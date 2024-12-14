package com.smart_cities.provider.repository;

import com.smart_cities.provider.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long>, JpaSpecificationExecutor<Consumption> {
}
