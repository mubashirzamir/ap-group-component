package com.smart_cities.city.controller;

import com.smart_cities.city.model.AggregatedConsumption;
import com.smart_cities.city.repository.AggregatedConsumptionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregatedConsumptionController {
    private final AggregatedConsumptionRepository consumptionRepository;

    public AggregatedConsumptionController(AggregatedConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @GetMapping("/aggregated-consumptions")
    public List<AggregatedConsumption> getAll() {
        return this.consumptionRepository.findAll();
    }
}
