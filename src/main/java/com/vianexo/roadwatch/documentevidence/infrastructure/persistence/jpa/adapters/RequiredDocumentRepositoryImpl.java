package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;
import com.vianexo.roadwatch.documentevidence.domain.repositories.RequiredDocumentRepository;
import com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories.RequiredDocumentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequiredDocumentRepositoryImpl implements RequiredDocumentRepository {

    private final RequiredDocumentPersistenceRepository persistenceRepository;

    public RequiredDocumentRepositoryImpl(RequiredDocumentPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public RequiredDocument save(RequiredDocument document) {
        return persistenceRepository.save(document);
    }

    @Override
    public List<RequiredDocument> findAll() {
        return persistenceRepository.findAll();
    }
}
