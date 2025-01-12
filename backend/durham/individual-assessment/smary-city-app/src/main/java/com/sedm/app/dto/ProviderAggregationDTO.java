package com.sedm.app.dto;

import lombok.Data;

@Data
public class ProviderAggregationDTO {
    private String providerName;
    private long totalCount;         // total number of powerConsumption entries
    private long sum;               // sum of all powerConsumption
    private double average;         // average of all powerConsumption
}
