package com.vianexo.roadwatch.incidentmitigation.domain.repositories;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EnvironmentalIncidentRepository {

    EnvironmentalIncident save(EnvironmentalIncident incident);

    Optional<EnvironmentalIncident> findById(Long id);

    boolean existsById(Long id);

    List<EnvironmentalIncident> findAll();

    List<EnvironmentalIncident> findByStatus(IncidentStatus status);

    List<EnvironmentalIncident> findBySeverity(IncidentSeverity severity);

    List<EnvironmentalIncident> findByStatusAndSeverity(IncidentStatus status, IncidentSeverity severity);

    List<EnvironmentalIncident> findBySeverityAndStatusNot(IncidentSeverity severity, IncidentStatus status);

    List<EnvironmentalIncident> findByStatusInAndCreatedAtBefore(Collection<IncidentStatus> statuses,
                                                                 LocalDateTime createdBefore);
}
