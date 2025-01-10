package com.kf7014.electricityprovidermicroservice.service;

import com.kf7014.electricityprovidermicroservice.model.MeterReading;
import com.kf7014.electricityprovidermicroservice.repository.MeterReadingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service class for managing meter readings in the Electricity Provider Microservice.
 * <p>
 * This service provides methods to save meter readings, retrieve readings based on various criteria,
 * and obtain the latest reading for a specific meter.
 * </p>
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Service
public class MeterReadingService {

    private final MeterReadingRepository repository;

    /**
     * Constructs a new {@code MeterReadingService} with the specified repository.
     *
     * @param repository the {@link MeterReadingRepository} used for data access operations
     */
    public MeterReadingService(MeterReadingRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a meter reading to the repository.
     *
     * <p>
     * This method persists a {@link MeterReading} object representing a meter's reading.
     * It is typically used for automated or smart meter readings.
     * </p>
     *
     * @param meterReading the {@link MeterReading} to be saved
     */
    public void saveMeterReading(MeterReading meterReading) {
        repository.save(meterReading);
    }

    /**
     * Saves a manual meter reading to the repository.
     *
     * <p>
     * This method persists a {@link MeterReading} object representing a manually submitted meter reading.
     * Additional logic specific to manual readings can be implemented within this method.
     * </p>
     *
     * @param meterReading the {@link MeterReading} to be saved manually
     */
    public void saveManualMeterReading(MeterReading meterReading) {
        // Additional logic for manual readings can be added here
        repository.save(meterReading);
    }

    /**
     * Retrieves all meter readings recorded since a specified timestamp.
     *
     * <p>
     * This method fetches a list of {@link MeterReading} objects that have a timestamp later than the provided
     * {@code since} parameter.
     * </p>
     *
     * @param since the {@link Instant} representing the starting point in time from which readings are retrieved
     * @return a {@link List} of {@link MeterReading} objects recorded after the specified timestamp
     */
    public List<MeterReading> getAllMeterReadingsSince(Instant since) {
        return repository.findByTimestampGreaterThan(since);
    }

    /**
     * Retrieves meter readings for a specific meter ID recorded since a specified timestamp.
     *
     * <p>
     * This method fetches a list of {@link MeterReading} objects associated with the given {@code meterId}
     * and have a timestamp later than the provided {@code since} parameter.
     * </p>
     *
     * @param meterId the unique identifier of the meter for which readings are to be retrieved
     * @param since   the {@link Instant} representing the starting point in time from which readings are retrieved
     * @return a {@link List} of {@link MeterReading} objects for the specified meter ID recorded after the specified timestamp
     */
    public List<MeterReading> getMeterReadingsByMeterIdSince(String meterId, Instant since) {
        return repository.findByMeterIdAndTimestampGreaterThan(meterId, since);
    }

    /**
     * Retrieves the latest meter reading for a specific meter ID.
     *
     * <p>
     * This method fetches the most recent {@link MeterReading} object associated with the given {@code meterId}.
     * If no readings are found for the specified meter ID, {@code null} is returned.
     * </p>
     *
     * @param meterId the unique identifier of the meter for which the latest reading is to be retrieved
     * @return the latest {@link MeterReading} object for the specified meter ID, or {@code null} if none found
     */
    public MeterReading getLatestMeterReadingByMeterId(String meterId) {
        return repository.findTopByMeterIdOrderByTimestampDesc(meterId);
    }
}
