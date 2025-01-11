package com.kf7014.citizenmicroservice.service;

import com.kf7014.citizenmicroservice.config.ProviderProperties;
import com.kf7014.citizenmicroservice.model.Meter;
import com.kf7014.citizenmicroservice.model.MeterReading;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;
import java.util.Random;

/**
 * Service class responsible for managing meter operations.
 * 
 * <p>This service handles the generation of automatic and manual meter readings,
 * initializes meters with the latest readings from the provider, and communicates
 * with the provider's API to send and receive meter data.</p>
 * 
 * <p>Scheduling annotations are used to periodically generate and send readings.</p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Service
public class MeterService {

    private final List<Meter> smartMeters;
    private final List<Meter> citizenMeters;
    private final WebClient webClient;
    private final String providerApiUrl;
    private final Random random = new Random();

    /**
     * Constructs a new {@code MeterService} with the specified dependencies.
     * 
     * @param smartMeters      The list of smart meters managed by the service.
     * @param citizenMeters    The list of citizen meters managed by the service.
     * @param providerProperties Configuration properties for the provider service.
     * @param webClient        The {@link WebClient} used for external API communication.
     */
    public MeterService(List<Meter> smartMeters, List<Meter> citizenMeters,
                             ProviderProperties providerProperties, WebClient webClient) {
        this.smartMeters = smartMeters;
        this.citizenMeters = citizenMeters;
        this.providerApiUrl = providerProperties.getUrl(); // Automatic readings API URL
        this.webClient = webClient;
        // Initialize last readings from the provider
        initializeLastReadings(this.smartMeters);
        initializeLastReadings(this.citizenMeters);
    }
    
    /**
     * Initializes the last readings for a list of meters by fetching the latest readings from the provider.
     * 
     * @param meters The list of {@link Meter} instances to initialize.
     */
    public void initializeLastReadings(List<Meter> meters) {
        for (Meter meter : meters) {
            double lastReading = fetchLatestReadingFromProvider(meter.getMeterId());
            meter.setLastReading(lastReading);
        }
    }
    
    /**
     * Fetches the latest reading for a specific meter from the provider's API.
     * 
     * @param meterId The unique identifier of the meter.
     * @return The latest reading as a {@code double}. Returns {@code 0.0} if no reading is found or an error occurs.
     */
    private double fetchLatestReadingFromProvider(String meterId) {
        String apiUrl = providerApiUrl + "/meter/" + meterId + "/latest";

        try {
            MeterReading reading = webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToMono(MeterReading.class)
                    .block();

            if (reading != null) {
                return reading.getReading();
            } else {
                // If no reading is found, start from 0.0
                return 0.0;
            }
        } catch (Exception e) {
            System.err.println("Error fetching latest reading for meter " + meterId + ": " + e.getMessage());
            // If there's an error, start from 0.0
            return 0.0;
        }
    }

    /**
     * Generates and sends automatic readings for all smart meters at a fixed rate.
     * 
     * <p>This method is scheduled to run every 60 seconds. It generates a new reading
     * for each smart meter and sends the data to the provider's API.</p>
     */
    @Scheduled(fixedRate = 120000)
    public void generateAndSendAutomaticReadings() {
        for (Meter meter : smartMeters) {
            double reading = meter.generateReading();
            sendDataToProvider(meter.getMeterId(), reading, false);
        }
    }

    /**
     * Simulates manual readings submitted by citizens at a fixed rate.
     * 
     * <p>This method is scheduled to run every 2 minutes. It simulates a random number
     * of citizens submitting readings for their meters and sends the data to the provider's API.</p>
     */
    @Scheduled(fixedRate = 120000)
    public void simulateCitizenManualReadings() {
        // Simulate a random number of citizens submitting readings
        int numberOfCitizens = random.nextInt(100) + 1; // Between 1 and 100
        for (int i = 0; i < numberOfCitizens; i++) {
            // Randomly select a citizen meter
            Meter citizenMeter = citizenMeters.get(random.nextInt(citizenMeters.size()));
            double reading = citizenMeter.generateReading();
            submitManualReading(citizenMeter.getMeterId(), reading);
        }
    }

    /**
     * Submits a manual reading for a specific meter to the provider's API.
     * 
     * @param meterId The unique identifier of the meter.
     * @param reading The reading value to submit.
     */
    public void submitManualReading(String meterId, double reading) {
        sendDataToProvider(meterId, reading, true);
    }

    /**
     * Sends meter reading data to the provider's API.
     * 
     * <p>Depending on whether the reading is manual or automatic, the data is sent
     * to the appropriate endpoint.</p>
     * 
     * @param meterId  The unique identifier of the meter.
     * @param reading  The reading value to send.
     * @param isManual {@code true} if the reading is manual; {@code false} otherwise.
     */
    private void sendDataToProvider(String meterId, double reading, boolean isManual) {
        MeterReading data = new MeterReading(meterId, reading, Instant.now().toString(), isManual);
        
        String providerManualApiUrl = this.providerApiUrl + "/manual";
        String providerAutoApiUrl = this.providerApiUrl + "/smartmeter";
        
        // Choose the correct API URL based on 'isManual'
        String apiUrl = isManual ? providerManualApiUrl : providerAutoApiUrl;

        webClient.post()
                .uri(apiUrl)
                .bodyValue(data)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(
                        success -> System.out.println("Sent " + (isManual ? "manual" : "automatic") +
                                " reading from meter " + meterId + " to Smart City Microservice at " + apiUrl),
                        error -> System.err.println("Error sending reading from meter " + meterId + ": " + error.getMessage())
                );
    }
}
