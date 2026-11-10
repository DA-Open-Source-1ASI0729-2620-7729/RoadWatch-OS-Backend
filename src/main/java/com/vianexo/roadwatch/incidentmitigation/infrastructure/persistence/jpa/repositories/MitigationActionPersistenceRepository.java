package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.MitigationActionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MitigationActionPersistenceRepository extends JpaRepository<MitigationActionPersistenceEntity, Long> {

    List<MitigationActionPersistenceEntity> findAllByIncidentIdOrderByIdAsc(Long incidentId);
}
