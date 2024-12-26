package com.smart_cities.city.controller;

import com.smart_cities.city.dto.*;
import com.smart_cities.city.model.AggregatedConsumption;
import com.smart_cities.city.repository.AggregatedConsumptionRepository;
import com.smart_cities.city.service.AggregatedConsumptionService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class AggregatedConsumptionController {
    private final AggregatedConsumptionRepository consumptionRepository;
    private final AggregatedConsumptionService consumptionService;

    public AggregatedConsumptionController(AggregatedConsumptionRepository consumptionRepository,
                                           AggregatedConsumptionService consumptionService) {
        this.consumptionRepository = consumptionRepository;
        this.consumptionService = consumptionService;
    }

    @GetMapping("/aggregated-consumptions")
    public List<AggregatedConsumption> getAll() {
        return this.consumptionRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    @GetMapping("/data/providers")
    public ResponseEntity<?> getAggregatedByProvider(@RequestParam(defaultValue = "LAST_30_DAYS") String timeRange) {
        return handleRequest(() -> {
            List<AggregatedConsumptionByProvider> result = this.consumptionService.getAggregatedByProvider(timeRange);
            return ResponseEntity.ok(result);
        });
    }

    @GetMapping("/data/city")
    public ResponseEntity<?> getAggregatedForCity(@RequestParam(defaultValue = "LAST_30_DAYS") String timeRange) {
        return handleRequest(() -> {
            AggregatedConsumptionForCity result = this.consumptionService.getAggregatedForCity(timeRange);
            return ResponseEntity.ok(result);
        });
    }

    @GetMapping("/graphs/monthly-average/providers")
    public ResponseEntity<?> getMonthlyAverageByProvider(@RequestParam(defaultValue = "2024") int year) {
        return handleRequest(() -> {
            List<MonthlyAverageByProvider> result = this.consumptionService.getMonthlyAverageByProvider(year);
            return ResponseEntity.ok(result);
        });
    }

    @GetMapping("/graphs/monthly-average/city")
    public ResponseEntity<?> getMonthlyAverageForCity(@RequestParam(defaultValue = "2024") int year) {
        return handleRequest(() -> {
            List<MonthlyAverageForCity> result = this.consumptionService.getMonthlyAverageForCity(year);
            return ResponseEntity.ok(result);
        });
    }

    private ResponseEntity<?> handleRequest(Supplier<ResponseEntity<?>> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.create(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.create(
                    "An unexpected error occurred."
            ));
        }
    }
}
