package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentCommentRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers.IncidentCommentPersistenceAssembler;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.EnvironmentalIncidentPersistenceRepository;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.repositories.IncidentCommentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncidentCommentRepositoryImpl implements IncidentCommentRepository {

    private final IncidentCommentPersistenceRepository persistenceRepository;
    private final EnvironmentalIncidentPersistenceRepository incidentPersistenceRepository;

    public IncidentCommentRepositoryImpl(IncidentCommentPersistenceRepository persistenceRepository,
                                         EnvironmentalIncidentPersistenceRepository incidentPersistenceRepository) {
        this.persistenceRepository = persistenceRepository;
        this.incidentPersistenceRepository = incidentPersistenceRepository;
    }

    @Override
    public IncidentComment save(IncidentComment comment) {
        var incident = incidentPersistenceRepository.getReferenceById(comment.getIncidentId());
        var saved = persistenceRepository.save(
                IncidentCommentPersistenceAssembler.toPersistenceFromDomain(comment, incident));
        return IncidentCommentPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public List<IncidentComment> findByIncidentId(Long incidentId) {
        return persistenceRepository.findAllByIncidentIdOrderByIdAsc(incidentId).stream()
                .map(IncidentCommentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
