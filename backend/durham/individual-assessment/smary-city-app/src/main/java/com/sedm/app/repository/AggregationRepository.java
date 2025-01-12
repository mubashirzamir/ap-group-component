package com.sedm.app.repository;

import com.sedm.app.entities.AggregationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AggregationRepository extends MongoRepository<AggregationEntity, String> {
    AggregationEntity findByProviderName(String providerName);
}
