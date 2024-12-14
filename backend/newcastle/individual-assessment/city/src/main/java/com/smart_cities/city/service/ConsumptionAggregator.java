package com.smart_cities.city.service;

import com.smart_cities.city.dto.Consumption;
import com.smart_cities.city.model.AggregatedConsumption;
import com.smart_cities.city.repository.AggregatedConsumptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsumptionAggregator {
    private final AggregatedConsumptionRepository aggregatedConsumptionRepository;

    public ConsumptionAggregator(AggregatedConsumptionRepository aggregatedConsumptionRepository) {
        this.aggregatedConsumptionRepository = aggregatedConsumptionRepository;
    }

    /**
     * Aggregates the consumption data.
     * <p>
     * The aggregation is done for each provider individually instead of for all providers so that if a request to
     * one provider fails, we can still get accurate data from the other providers for that interval. The idea being
     * that, we can further aggregate the data from all providers by using custom queries.
     * <p>
     * The aggregation is done by calculating the total and average consumption.
     *
     * @param consumptions   The list of consumption data.
     * @param providerId     The ID of the provider.
     * @param periodStart    The start of the consumption period.
     * @param periodEnd      The end of the consumption period.
     */
    public final void aggregate(
            List<Consumption> consumptions,
            Long providerId,
            LocalDateTime periodStart,
            LocalDateTime periodEnd
    ) {
        Long totalConsumption = 0L;
        Long averageConsumption = 0L;

        for (Consumption consumption : consumptions) {
            totalConsumption += consumption.getConsumption();
        }

        averageConsumption = totalConsumption / consumptions.size();

        AggregatedConsumption aggregatedConsumption = new AggregatedConsumption();
        aggregatedConsumption.setProviderId(providerId);
        aggregatedConsumption.setTotalConsumption(totalConsumption);
        aggregatedConsumption.setAverageConsumption(averageConsumption);
        aggregatedConsumption.setConsumptionPeriodStart(periodStart);
        aggregatedConsumption.setConsumptionPeriodEnd(periodEnd);

        this.aggregatedConsumptionRepository.save(aggregatedConsumption);
    }
}
