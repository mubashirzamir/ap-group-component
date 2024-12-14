package com.smart_cities.provider.controller;

import com.smart_cities.provider.model.Consumption;
import com.smart_cities.provider.repository.ConsumptionRepository;
import com.smart_cities.provider.specification.ConsumptionFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsumptionController {
    private final ConsumptionRepository consumptionRepository;

    public ConsumptionController(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @GetMapping("/consumptions")
    public List<Consumption> getAllConsumptions(@ModelAttribute ConsumptionFilter consumptionFilter) {
        return this.consumptionRepository.findAll(consumptionFilter);
    }

    @PostMapping("/consumptions")
    public ResponseEntity<Consumption> createConsumption(@RequestBody Consumption newConsumption) {
        return new ResponseEntity<>(this.consumptionRepository.save(newConsumption), HttpStatus.CREATED);
    }
}
