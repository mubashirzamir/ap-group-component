package com.smart_cities.city.service;

import com.smart_cities.city.dto.AggregatedConsumptionByProvider;
import com.smart_cities.city.dto.AggregatedConsumptionForCity;
import com.smart_cities.city.dto.MonthlyAverageByProvider;
import com.smart_cities.city.dto.MonthlyAverageForCity;
import com.smart_cities.city.model.AggregatedConsumption;
import com.smart_cities.city.repository.AggregatedConsumptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AggregatedConsumptionService {

    private final AggregatedConsumptionRepository repository;

    public AggregatedConsumptionService(AggregatedConsumptionRepository repository) {
        this.repository = repository;
    }

    public List<AggregatedConsumptionByProvider> getAggregatedByProvider(String timeRange) {
        LocalDateTime[] range = calculateTimeRange(timeRange);
        if (range == null) {
            throw new IllegalArgumentException("Invalid time range specified.");
        }
        return repository.findAggregatedByProvider(range[0], range[1]);
    }

    public AggregatedConsumptionForCity getAggregatedForCity(String timeRange) {
        LocalDateTime[] range = calculateTimeRange(timeRange);
        if (range == null) {
            throw new IllegalArgumentException("Invalid time range specified.");
        }
        return repository.findAggregatedForCity(range[0], range[1]);
    }

    public List<MonthlyAverageByProvider> getMonthlyAverageByProvider(int year) {
        if (year < 1900 || year > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Invalid year specified.");
        }
        return repository.findMonthlyAverageByProvider(year);
    }

    public List<MonthlyAverageForCity> getMonthlyAverageForCity(int year) {
        if (year < 1900 || year > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Invalid year specified.");
        }
        return repository.findMonthlyAverageForCity(year);
    }

    private LocalDateTime[] calculateTimeRange(String timeRange) {
        LocalDateTime now = LocalDateTime.now();
        return switch (timeRange) {
            case "LAST_24_HOURS" -> new LocalDateTime[]{now.minusDays(1), now};
            case "LAST_7_DAYS" -> new LocalDateTime[]{now.minusDays(7), now};
            case "LAST_30_DAYS" -> new LocalDateTime[]{now.minusDays(30), now};
            default -> null;
        };
    }
}
