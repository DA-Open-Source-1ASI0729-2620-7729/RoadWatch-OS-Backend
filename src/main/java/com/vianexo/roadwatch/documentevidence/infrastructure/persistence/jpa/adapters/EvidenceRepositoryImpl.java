package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;
import com.vianexo.roadwatch.documentevidence.domain.repositories.EvidenceRepository;
import com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories.EvidencePersistenceRepository;

import java.util.List;
import java.util.Optional;

public class EvidenceRepositoryImpl implements EvidenceRepository {

    private final EvidencePersistenceRepository persistenceRepository;

    public EvidenceRepositoryImpl(EvidencePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Evidence save(Evidence evidence) {
        return persistenceRepository.save(evidence);
    }

    @Override
    public List<Evidence> findAll() {
        return persistenceRepository.findAll();
    }

    @Override
    public Optional<Evidence> findById(Long id) {
        return persistenceRepository.findById(id);
    }

}
