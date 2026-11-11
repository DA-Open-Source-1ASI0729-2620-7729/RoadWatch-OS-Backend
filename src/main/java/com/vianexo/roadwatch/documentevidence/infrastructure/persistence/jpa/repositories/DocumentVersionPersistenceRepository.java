package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.entities.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentVersionPersistenceRepository extends JpaRepository<DocumentVersion, Long> {
    List<DocumentVersion> findByDocumentId(Long documentId);
}
