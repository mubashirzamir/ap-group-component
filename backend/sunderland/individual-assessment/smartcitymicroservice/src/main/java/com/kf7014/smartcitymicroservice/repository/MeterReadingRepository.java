package com.kf7014.smartcitymicroservice.repository;

import com.kf7014.smartcitymicroservice.model.MeterReading;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link MeterReading} entities.
 */
@Repository
public interface MeterReadingRepository extends MongoRepository<MeterReading, String>, MeterReadingRepositoryCustom {
    
    /**
     * Retrieves all meter readings for a specific provider within the given time range.
     *
     * @param providerId the unique identifier of the provider
     * @param startTime  the start time of the range
     * @param endTime    the end time of the range
     * @return a list of {@link MeterReading} matching the criteria
     */
    List<MeterReading> findByProviderIdAndTimestampBetween(String providerId, Date startTime, Date endTime);
    
    /**
     * Retrieves all meter readings within the given time range.
     *
     * @param startTime the start time of the range
     * @param endTime   the end time of the range
     * @return a list of {@link MeterReading} matching the criteria
     */
    List<MeterReading> findByTimestampBetween(Date startTime, Date endTime);
    
    /**
     * Retrieves the latest meter reading for a specific provider.
     *
     * @param providerId the unique identifier of the provider
     * @return an {@link Optional} containing the latest {@link MeterReading} if present
     */
    Optional<MeterReading> findTopByProviderIdOrderByTimestampDesc(String providerId);
}
