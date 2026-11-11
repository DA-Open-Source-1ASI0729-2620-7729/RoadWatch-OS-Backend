package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.IncidentStatusHistoryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentStatusHistoryPersistenceRepository
        extends JpaRepository<IncidentStatusHistoryPersistenceEntity, Long> {

    List<IncidentStatusHistoryPersistenceEntity> findAllByIncidentIdOrderByIdAsc(Long incidentId);
}
