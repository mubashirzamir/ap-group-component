package com.sedm.app.repository;

import com.sedm.app.entities.ReadingEPEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingEPRepository extends MongoRepository<ReadingEPEntity,String> {

    ReadingEPEntity findByUserId(String userId);
}
