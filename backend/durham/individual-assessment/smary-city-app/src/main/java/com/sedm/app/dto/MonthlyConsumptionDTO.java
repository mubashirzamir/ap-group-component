package com.sedm.app.dto;

import lombok.Data;

@Data
public class MonthlyConsumptionDTO {
    private String month;           // "yyyy-MM", e.g., "2024-01"
    private long totalConsumption;  // sum of powerConsumption for that month
}
