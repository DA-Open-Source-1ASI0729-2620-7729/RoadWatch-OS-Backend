package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidencePersistenceRepository extends JpaRepository<Evidence, Long> {

    List<Evidence> findByIncidentId(Long incidentId);

    List<Evidence> findByMeasurementId(Long measurementId);

    boolean existsByIncidentId(Long incidentId);
}



