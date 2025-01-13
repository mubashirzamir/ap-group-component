package com.sedm.app.repository;

import com.sedm.app.entities.ReadingSCEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingSCRepository extends MongoRepository<ReadingSCEntity, String> {
}
