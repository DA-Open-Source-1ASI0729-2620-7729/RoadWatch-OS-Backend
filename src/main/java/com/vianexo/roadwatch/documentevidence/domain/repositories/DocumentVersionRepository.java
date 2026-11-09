package com.vianexo.roadwatch.documentevidence.domain.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.entities.DocumentVersion;

import java.util.List;

public interface DocumentVersionRepository {

    DocumentVersion save(DocumentVersion version);

    List<DocumentVersion> findByDocumentId(Long documentId);
}
