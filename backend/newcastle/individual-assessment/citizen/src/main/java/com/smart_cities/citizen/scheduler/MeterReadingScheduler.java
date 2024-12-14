package com.smart_cities.citizen.scheduler;

import com.smart_cities.citizen.model.Meter;
import com.smart_cities.citizen.repository.MeterRepository;
import com.smart_cities.citizen.service.ProviderNotifier;
import com.smart_cities.citizen.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeterReadingScheduler {
    private final ProviderNotifier providerNotifier;
    private final MeterRepository meterRepository;
    private final ReadingService readingService;
    private List<Meter> meters = new ArrayList<>();

    public List<Meter> getMeters() {
        return meters;
    }

    public void setMeters(List<Meter> meters) {
        this.meters = meters;
    }

    @Autowired
    public MeterReadingScheduler(ProviderNotifier providerNotifier,
                                 MeterRepository meterRepository,
                                 ReadingService readingService) {
        this.providerNotifier = providerNotifier;
        this.meterRepository = meterRepository;
        this.readingService = readingService;
        this.init();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        this.setMeters(this.meterRepository.findAll());
    }

    /**
     * Generates readings for all smart meters and notifies the provider.
     * The readings are generated every 10 seconds.
     */
    @Scheduled(fixedRate = 10000)
    public void generateAndNotify() {
        for (Meter meter : this.getMeters()) {
            this.providerNotifier.notify(
                    this.readingService.generateReading(meter).toPostPayload(),
                    meter.getProviderId()
            );
        }
    }
}
