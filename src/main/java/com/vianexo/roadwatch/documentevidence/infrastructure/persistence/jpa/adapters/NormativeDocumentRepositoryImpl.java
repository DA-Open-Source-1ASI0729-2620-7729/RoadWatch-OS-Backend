package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import com.vianexo.roadwatch.documentevidence.domain.repositories.NormativeDocumentRepository;
import com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories.NormativeDocumentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NormativeDocumentRepositoryImpl implements NormativeDocumentRepository {

    private final NormativeDocumentPersistenceRepository persistenceRepository;

    public NormativeDocumentRepositoryImpl(NormativeDocumentPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public NormativeDocument save(NormativeDocument document) {
        return persistenceRepository.save(document);
    }

    @Override
    public List<NormativeDocument> findAll() {
        return persistenceRepository.findAll();
    }

    @Override
    public Optional<NormativeDocument> findById(Long id) {
        return persistenceRepository.findById(id);
    }
}
