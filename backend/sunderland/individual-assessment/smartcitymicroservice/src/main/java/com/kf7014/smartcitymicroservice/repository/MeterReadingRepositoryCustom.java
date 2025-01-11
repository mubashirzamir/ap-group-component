package com.kf7014.smartcitymicroservice.repository;

import java.util.List;

/**
 * Custom repository interface for {@link com.kf7014.smartcitymicroservice.model.MeterReading} entities.
 * <p>
 * Defines custom query methods that are not part of the standard {@link MeterReadingRepository} interface.
 * Implementations of this interface provide the logic for these custom methods.
 * </p>
 * 
 * @see MeterReadingRepositoryImpl
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
public interface MeterReadingRepositoryCustom {

    /**
     * Retrieves a list of distinct provider IDs from the meter readings.
     *
     * @return a list of unique provider IDs
     */
    List<String> findDistinctProviderIds();
}
