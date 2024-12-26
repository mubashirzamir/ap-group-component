package com.smart_cities.city.repository;

import com.smart_cities.city.dto.AggregatedConsumptionByProvider;
import com.smart_cities.city.dto.AggregatedConsumptionForCity;
import com.smart_cities.city.dto.MonthlyAverageByProvider;
import com.smart_cities.city.dto.MonthlyAverageForCity;
import com.smart_cities.city.model.AggregatedConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AggregatedConsumptionRepository extends JpaRepository<AggregatedConsumption, Long> {
    @Query("SELECT new com.smart_cities.city.dto.AggregatedConsumptionByProvider(a.providerId, SUM(a.totalConsumption), AVG(a.averageConsumption)) " +
            "FROM AggregatedConsumption a " +
            "WHERE a.consumptionPeriodStart >= :start AND a.consumptionPeriodEnd <= :end " +
            "GROUP BY a.providerId")
    List<AggregatedConsumptionByProvider> findAggregatedByProvider(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.smart_cities.city.dto.AggregatedConsumptionForCity(SUM(a.totalConsumption), AVG(a.averageConsumption)) " +
            "FROM AggregatedConsumption a " +
            "WHERE a.consumptionPeriodStart >= :start AND a.consumptionPeriodEnd <= :end")
    AggregatedConsumptionForCity findAggregatedForCity(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.smart_cities.city.dto.MonthlyAverageByProvider(a.providerId, MONTH(a.consumptionPeriodStart), AVG(a.averageConsumption)) " +
            "FROM AggregatedConsumption a " +
            "WHERE YEAR(a.consumptionPeriodStart) = :year " +
            "GROUP BY a.providerId, MONTH(a.consumptionPeriodStart)")
    List<MonthlyAverageByProvider> findMonthlyAverageByProvider(int year);

    @Query("SELECT new com.smart_cities.city.dto.MonthlyAverageForCity(MONTH(a.consumptionPeriodStart), AVG(a.averageConsumption)) " +
            "FROM AggregatedConsumption a " +
            "WHERE YEAR(a.consumptionPeriodStart) = :year " +
            "GROUP BY MONTH(a.consumptionPeriodStart)")
    List<MonthlyAverageForCity> findMonthlyAverageForCity(int year);
}
