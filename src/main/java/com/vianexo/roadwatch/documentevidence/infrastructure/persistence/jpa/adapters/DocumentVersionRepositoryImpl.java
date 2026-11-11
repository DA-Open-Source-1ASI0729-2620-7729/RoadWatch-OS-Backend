package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.documentevidence.domain.model.entities.DocumentVersion;
import com.vianexo.roadwatch.documentevidence.domain.repositories.DocumentVersionRepository;
import com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories.DocumentVersionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentVersionRepositoryImpl implements DocumentVersionRepository {

    private final DocumentVersionPersistenceRepository persistenceRepository;

    public DocumentVersionRepositoryImpl(DocumentVersionPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public DocumentVersion save(DocumentVersion version) {
        return persistenceRepository.save(version);
    }

    @Override
    public List<DocumentVersion> findByDocumentId(Long documentId) {
        return persistenceRepository.findByDocumentId(documentId);
    }
}
