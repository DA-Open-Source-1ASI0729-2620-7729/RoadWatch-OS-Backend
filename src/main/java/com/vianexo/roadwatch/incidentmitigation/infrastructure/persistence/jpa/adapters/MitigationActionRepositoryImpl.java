package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.MitigationActionRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers.MitigationActionPersistenceAssembler;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.MitigationActionPersistenceEntity;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.EnvironmentalIncidentPersistenceRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.MitigationActionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class MitigationActionRepositoryImpl implements MitigationActionRepository {

    private final MitigationActionPersistenceRepository persistenceRepository;
    private final EnvironmentalIncidentPersistenceRepository incidentPersistenceRepository;

    public MitigationActionRepositoryImpl(MitigationActionPersistenceRepository persistenceRepository,
                                          EnvironmentalIncidentPersistenceRepository incidentPersistenceRepository) {
        this.persistenceRepository = persistenceRepository;
        this.incidentPersistenceRepository = incidentPersistenceRepository;
    }

    @Override
    public MitigationAction save(MitigationAction action) {
        var entity = action.getId() == null
                ? new MitigationActionPersistenceEntity()
                : persistenceRepository.findById(action.getId())
                .orElseThrow(() -> new NoSuchElementException("Mitigation action not found: " + action.getId()));
        var incident = incidentPersistenceRepository.getReferenceById(action.getIncidentId());
        var saved = persistenceRepository.save(
                MitigationActionPersistenceAssembler.toPersistenceFromDomain(action, incident, entity));
        return MitigationActionPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<MitigationAction> findById(Long id) {
        return persistenceRepository.findById(id).map(MitigationActionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<MitigationAction> findByIncidentId(Long incidentId) {
        return persistenceRepository.findAllByIncidentIdOrderByIdAsc(incidentId).stream()
                .map(MitigationActionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
