package com.smart_cities.city.scheduler;

import com.smart_cities.city.service.ConsumptionAggregator;
import com.smart_cities.city.service.ProviderRequester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProviderPoller {
    private final ProviderRequester providerRequester;
    private final ConsumptionAggregator consumptionAggregator;
    private LocalDateTime consumptionPeriodStart;
    private LocalDateTime consumptionPeriodEnd;

    @Autowired
    public ProviderPoller(ProviderRequester providerRequester, ConsumptionAggregator consumptionAggregator) {
        this.providerRequester = providerRequester;
        this.consumptionAggregator = consumptionAggregator;
    }

    public LocalDateTime getConsumptionPeriodStart() {
        return this.consumptionPeriodStart;
    }

    public void setConsumptionPeriodStart(LocalDateTime consumptionPeriodStart) {
        this.consumptionPeriodStart = consumptionPeriodStart;
    }

    public LocalDateTime getConsumptionPeriodEnd() {
        return this.consumptionPeriodEnd;
    }

    public void setConsumptionPeriodEnd(LocalDateTime consumptionPeriodEnd) {
        this.consumptionPeriodEnd = consumptionPeriodEnd;
    }

    /**
     * Polls the providers for the consumption data and aggregates it.
     * The polling is done every 2 minutes.
     */
    @Scheduled(fixedRate = 120000)
    public void pollAndAggregate() {
        this.setConsumptionPeriod();

        this.providerRequester.request(1L, this.getConsumptionPeriodStart(), this.getConsumptionPeriodEnd())
                .thenAccept(consumptions -> this.consumptionAggregator.aggregate(
                        consumptions,
                        1L,
                        this.getConsumptionPeriodStart(),
                        this.getConsumptionPeriodEnd())
                );
        this.providerRequester.request(2L, this.getConsumptionPeriodStart(), this.getConsumptionPeriodEnd())
                .thenAccept(consumptions -> this.consumptionAggregator.aggregate(
                        consumptions,
                        2L,
                        this.getConsumptionPeriodStart(),
                        this.getConsumptionPeriodEnd())
                );
        this.providerRequester.request(3L, this.getConsumptionPeriodStart(), this.getConsumptionPeriodEnd())
                .thenAccept(consumptions -> this.consumptionAggregator.aggregate(
                        consumptions,
                        3L,
                        this.getConsumptionPeriodStart(),
                        this.getConsumptionPeriodEnd())
                );
    }

    /**
     * Sets the consumption period to the last 2 minutes.
     */
    public void setConsumptionPeriod() {
        this.setConsumptionPeriodStart(LocalDateTime.now().minusMinutes(4));
        this.setConsumptionPeriodEnd(LocalDateTime.now().minusMinutes(2));
    }
}
