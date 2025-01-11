package com.kf7014.smartcitymicroservice.repository;

import com.kf7014.smartcitymicroservice.model.MeterReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link MeterReadingRepositoryCustom} interface.
 * <p>
 * Provides concrete implementations for custom repository methods defined in {@link MeterReadingRepositoryCustom}.
 * </p>
 * 
 * <p>
 * Utilizes {@link MongoTemplate} to perform custom queries that are not supported by method naming conventions.
 * </p>
 * 
 * @see MeterReadingRepositoryCustom
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Repository
public class MeterReadingRepositoryImpl implements MeterReadingRepositoryCustom {

    /**
     * The {@link MongoTemplate} used for executing custom MongoDB queries.
     */
    private final MongoTemplate mongoTemplate;

    /**
     * Constructs a new {@code MeterReadingRepositoryImpl} with the specified {@link MongoTemplate}.
     *
     * @param mongoTemplate the {@link MongoTemplate} to use for database operations
     */
    @Autowired
    public MeterReadingRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Retrieves a list of distinct provider IDs from the meter readings collection.
     *
     * @return a {@link List} of unique provider IDs as {@link String} objects
     */
    @Override
    public List<String> findDistinctProviderIds() {
        return mongoTemplate.query(MeterReading.class)
                .distinct("providerId")
                .as(String.class)
                .all();
    }
}
