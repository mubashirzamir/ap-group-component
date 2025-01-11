package com.kf7014.smartcitymicroservice.service;

import com.kf7014.smartcitymicroservice.config.ProviderProperties;
import com.kf7014.smartcitymicroservice.model.MeterReading;
import com.kf7014.smartcitymicroservice.repository.MeterReadingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Service responsible for collecting meter readings from external providers and storing them in the database.
 * <p>
 * This service periodically fetches data from configured providers using scheduled tasks,
 * processes the retrieved meter readings, and persists them to MongoDB.
 * </p>
 * 
 * <p>
 * The {@link #collectData()} method is scheduled to run every 5 minutes, iterating through each configured provider,
 * fetching new readings since the last recorded timestamp, and storing them.
 * </p>
 * 
 * <p>
 * Handles potential duplicate entries by catching {@link DuplicateKeyException} and logging appropriate messages.
 * </p>
 * 
 * <p>
 * Utilizes {@link WebClient} for reactive HTTP requests to external provider APIs.
 * </p>
 * 
 * @author Muhammad
 * @version 1.0
 * @since 2024-11-27
 */
@Service
public class DataCollectionService {

    /**
     * Configuration properties for external service providers.
     */
    private final ProviderProperties providerProperties;

    /**
     * Repository for {@link MeterReading} entities, used for data persistence operations.
     */
    private final MeterReadingRepository meterReadingRepository;

    /**
     * WebClient instance for making HTTP requests to external provider APIs.
     */
    private final WebClient webClient;

    /**
     * Logger instance for logging information and errors.
     */
    private static final Logger logger = LoggerFactory.getLogger(DataCollectionService.class);

    /**
     * Constructs a new {@code DataCollectionService} with the specified dependencies.
     *
     * @param providerProperties      the {@link ProviderProperties} containing provider configurations
     * @param meterReadingRepository  the {@link MeterReadingRepository} for persisting meter readings
     */
    public DataCollectionService(ProviderProperties providerProperties,
                                 MeterReadingRepository meterReadingRepository) {
        this.providerProperties = providerProperties;
        this.meterReadingRepository = meterReadingRepository;
        this.webClient = WebClient.create();
    }

    /**
     * Scheduled task that collects meter readings from all configured providers every 5 minutes.
     * <p>
     * Iterates through each provider defined in {@link ProviderProperties}, fetches new meter readings
     * since the last recorded timestamp, and stores them in the database.
     * </p>
     */
    @Scheduled(fixedRate = 300000) // Every 5 minutes (300,000 milliseconds)
    public void collectData() {
        logger.info("Starting scheduled data collection task.");
        for (ProviderProperties.ProviderConfig provider : providerProperties.getProviders()) {
            fetchAndStoreData(provider);
        }
        logger.info("Completed scheduled data collection task.");
    }

    /**
     * Fetches and stores meter readings from a specific provider.
     * <p>
     * Constructs the API URL using the provider's base URL and the last fetched timestamp,
     * retrieves the meter readings, assigns the provider ID to each reading, and persists them.
     * </p>
     *
     * @param provider the {@link ProviderProperties.ProviderConfig} containing provider details
     */
    private void fetchAndStoreData(ProviderProperties.ProviderConfig provider) {
        String providerId = provider.getId();
        logger.info("Fetching data for provider: {}", providerId);

        // Retrieve the last fetched timestamp from the meter readings
        String lastFetchedTimestamp = getLastFetchedTimestamp(providerId);
        logger.info("Last fetched timestamp for provider {}: {}", providerId, lastFetchedTimestamp);

        String apiUrl = provider.getBaseUrl() + "/api/readings?since=" + lastFetchedTimestamp;

        try {
            // Fetch data from the provider's API
            List<MeterReading> readings = webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToFlux(MeterReading.class)
                    .collectList()
                    .block();

            if (readings != null && !readings.isEmpty()) {
                // Set providerId for each reading
                readings.forEach(reading -> reading.setProviderId(providerId));

                // Store readings in the database
                try {
                    meterReadingRepository.saveAll(readings);
                    logger.info("Collected and stored {} readings from {}", readings.size(), providerId);
                } catch (DuplicateKeyException e) {
                    logger.error("Duplicate readings detected for provider {}: {}", providerId, e.getMessage());
                    // Optionally, implement logic to handle duplicates, such as skipping or updating existing entries
                }
            } else {
                logger.info("No new readings received from {}", providerId);
            }
        } catch (Exception e) {
            logger.error("Error fetching data from {}: {}", providerId, e.getMessage());
            // Optionally, implement retry logic or handle the exception as needed
        }
    }

    /**
     * Retrieves the latest fetched timestamp for a specific provider.
     * <p>
     * Queries the database for the most recent meter reading associated with the given provider ID.
     * If no readings exist, returns a default initial timestamp.
     * </p>
     *
     * @param providerId the unique identifier of the provider
     * @return the timestamp of the latest meter reading in ISO-8601 {@code "YYYY-MM-DDTHH:MM:SSZ"} format,
     *         or {@code "1970-01-01T00:00:00Z"} if no readings are found
     */
    private String getLastFetchedTimestamp(String providerId) {
        logger.info("Fetching last fetched timestamp for provider: {}", providerId);

        Optional<MeterReading> latestReadingOpt = meterReadingRepository.findTopByProviderIdOrderByTimestampDesc(providerId);

        if (latestReadingOpt.isPresent()) {
            Date timestamp = latestReadingOpt.get().getTimestamp();
            String formattedTimestamp = formatDate(timestamp);
            logger.info("Latest timestamp for provider {}: {}", providerId, formattedTimestamp);
            return formattedTimestamp;
        } else {
            logger.warn("No meter readings found for provider {}. Using default timestamp.", providerId);
            // Default initial timestamp if no readings exist
            return "1970-01-01T00:00:00Z";
        }
    }

    /**
     * Formats a {@link Date} object into an ISO-8601 formatted {@code String}.
     *
     * @param date the {@link Date} to format
     * @return the formatted date string in ISO-8601 format
     */
    private String formatDate(Date date) {
        // Define the desired date format, e.g., ISO 8601
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}
