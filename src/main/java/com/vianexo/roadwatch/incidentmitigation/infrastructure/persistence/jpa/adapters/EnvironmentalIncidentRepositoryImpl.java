package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers.EnvironmentalIncidentPersistenceAssembler;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.EnvironmentalIncidentPersistenceEntity;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.EnvironmentalIncidentPersistenceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class EnvironmentalIncidentRepositoryImpl implements EnvironmentalIncidentRepository {

    private final EnvironmentalIncidentPersistenceRepository persistenceRepository;

    public EnvironmentalIncidentRepositoryImpl(EnvironmentalIncidentPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public EnvironmentalIncident save(EnvironmentalIncident incident) {
        var entity = incident.getId() == null
                ? new EnvironmentalIncidentPersistenceEntity()
                : persistenceRepository.findById(incident.getId())
                .orElseThrow(() -> new NoSuchElementException("Incident not found: " + incident.getId()));
        var saved = persistenceRepository.save(
                EnvironmentalIncidentPersistenceAssembler.toPersistenceFromDomain(incident, entity));
        return EnvironmentalIncidentPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<EnvironmentalIncident> findById(Long id) {
        return persistenceRepository.findById(id).map(EnvironmentalIncidentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public List<EnvironmentalIncident> findAll() {
        return toDomain(persistenceRepository.findAll(Sort.by("id")));
    }

    @Override
    public List<EnvironmentalIncident> findByStatus(IncidentStatus status) {
        return toDomain(persistenceRepository.findAllByStatusOrderByIdAsc(status));
    }

    @Override
    public List<EnvironmentalIncident> findBySeverity(IncidentSeverity severity) {
        return toDomain(persistenceRepository.findAllBySeverityOrderByIdAsc(severity));
    }

    @Override
    public List<EnvironmentalIncident> findByStatusAndSeverity(IncidentStatus status, IncidentSeverity severity) {
        return toDomain(persistenceRepository.findAllByStatusAndSeverityOrderByIdAsc(status, severity));
    }

    @Override
    public List<EnvironmentalIncident> findBySeverityAndStatusNot(IncidentSeverity severity, IncidentStatus status) {
        return toDomain(persistenceRepository.findAllBySeverityAndStatusNotOrderByIdAsc(severity, status));
    }

    @Override
    public List<EnvironmentalIncident> findByStatusInAndCreatedAtBefore(Collection<IncidentStatus> statuses,
                                                                        LocalDateTime createdBefore) {
        return toDomain(persistenceRepository.findAllByStatusInAndCreatedAtBefore(statuses,
                EnvironmentalIncidentPersistenceAssembler.toDate(createdBefore)));
    }

    private static List<EnvironmentalIncident> toDomain(List<EnvironmentalIncidentPersistenceEntity> entities) {
        return entities.stream().map(EnvironmentalIncidentPersistenceAssembler::toDomainFromPersistence).toList();
    }
}
