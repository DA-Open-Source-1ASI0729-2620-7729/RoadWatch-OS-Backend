package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.IncidentCommentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentCommentPersistenceRepository extends JpaRepository<IncidentCommentPersistenceEntity, Long> {

    List<IncidentCommentPersistenceEntity> findAllByIncidentIdOrderByIdAsc(Long incidentId);
}
