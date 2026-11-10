package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentStatusHistoryRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers.IncidentStatusHistoryPersistenceAssembler;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.EnvironmentalIncidentPersistenceRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.IncidentStatusHistoryPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncidentStatusHistoryRepositoryImpl implements IncidentStatusHistoryRepository {

    private final IncidentStatusHistoryPersistenceRepository persistenceRepository;
    private final EnvironmentalIncidentPersistenceRepository incidentPersistenceRepository;

    public IncidentStatusHistoryRepositoryImpl(IncidentStatusHistoryPersistenceRepository persistenceRepository,
                                               EnvironmentalIncidentPersistenceRepository incidentPersistenceRepository) {
        this.persistenceRepository = persistenceRepository;
        this.incidentPersistenceRepository = incidentPersistenceRepository;
    }

    @Override
    public IncidentStatusHistory save(IncidentStatusHistory history) {
        var incident = incidentPersistenceRepository.getReferenceById(history.getIncidentId());
        var saved = persistenceRepository.save(
                IncidentStatusHistoryPersistenceAssembler.toPersistenceFromDomain(history, incident));
        return IncidentStatusHistoryPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public List<IncidentStatusHistory> findByIncidentId(Long incidentId) {
        return persistenceRepository.findAllByIncidentIdOrderByIdAsc(incidentId).stream()
                .map(IncidentStatusHistoryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
