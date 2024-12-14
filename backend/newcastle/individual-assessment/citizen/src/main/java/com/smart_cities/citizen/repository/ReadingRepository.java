package com.smart_cities.citizen.repository;

import com.smart_cities.citizen.model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    Optional<Reading> findByEntityIdAndEntityType(Long entityId, String entityType);
}
