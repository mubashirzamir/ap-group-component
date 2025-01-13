package com.sedm.app.repository;

import com.sedm.app.entities.ReadingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingRepository extends MongoRepository<ReadingEntity, String> {

    Optional<ReadingEntity> findByUserId(String userId);
}
