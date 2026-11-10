package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.EnvironmentalIncidentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EnvironmentalIncidentPersistenceRepository
        extends JpaRepository<EnvironmentalIncidentPersistenceEntity, Long> {

    List<EnvironmentalIncidentPersistenceEntity> findAllByStatusOrderByIdAsc(IncidentStatus status);

    List<EnvironmentalIncidentPersistenceEntity> findAllBySeverityOrderByIdAsc(IncidentSeverity severity);

    List<EnvironmentalIncidentPersistenceEntity> findAllByStatusAndSeverityOrderByIdAsc(IncidentStatus status,
                                                                                         IncidentSeverity severity);

    List<EnvironmentalIncidentPersistenceEntity> findAllBySeverityAndStatusNotOrderByIdAsc(IncidentSeverity severity,
                                                                                            IncidentStatus status);

    List<EnvironmentalIncidentPersistenceEntity> findAllByStatusInAndCreatedAtBefore(Collection<IncidentStatus> statuses,
                                                                                      Date createdBefore);
}
