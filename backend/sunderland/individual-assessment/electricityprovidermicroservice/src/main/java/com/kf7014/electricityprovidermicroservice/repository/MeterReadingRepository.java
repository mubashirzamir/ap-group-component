package com.kf7014.electricityprovidermicroservice.repository;

import com.kf7014.electricityprovidermicroservice.model.MeterReading;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Repository interface for {@link MeterReading} entities.
 * <p>
 * Provides CRUD operations and custom query methods for accessing meter readings stored in MongoDB.
 * </p>
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Repository
public interface MeterReadingRepository extends MongoRepository<MeterReading, String> {

    /**
     * Retrieves all meter readings for a specific meter ID recorded after a given timestamp.
     *
     * @param meterId  the unique identifier of the meter
     * @param timestamp the {@link Instant} representing the starting point in time
     * @return a {@link List} of {@link MeterReading} objects matching the criteria
     */
    List<MeterReading> findByMeterIdAndTimestampGreaterThan(String meterId, Instant timestamp);

    /**
     * Retrieves the latest meter reading for a specific meter ID.
     *
     * @param meterId the unique identifier of the meter
     * @return the latest {@link MeterReading} for the specified meter ID, or {@code null} if none found
     */
    MeterReading findTopByMeterIdOrderByTimestampDesc(String meterId);

    /**
     * Retrieves all meter readings recorded after a given timestamp.
     *
     * @param timestamp the {@link Instant} representing the starting point in time
     * @return a {@link List} of {@link MeterReading} objects recorded after the specified timestamp
     */
    List<MeterReading> findByTimestampGreaterThan(Instant timestamp);
}
