package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import com.vianexo.roadwatch.documentevidence.domain.model.entities.DocumentVersion;
import com.vianexo.roadwatch.documentevidence.domain.repositories.DocumentVersionRepository;
import com.vianexo.roadwatch.documentevidence.domain.repositories.NormativeDocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NormativeDocumentService {

    private final NormativeDocumentRepository documentRepository;
    private final DocumentVersionRepository versionRepository;

    public NormativeDocumentService(NormativeDocumentRepository documentRepository,
                                    DocumentVersionRepository versionRepository) {
        this.documentRepository = documentRepository;
        this.versionRepository = versionRepository;
    }

    public List<NormativeDocument> findAll() {
        return documentRepository.findAll();
    }

    public Optional<NormativeDocument> findById(Long id) {
        return documentRepository.findById(id);
    }

    public NormativeDocument create(Long projectId, String name) {
        return documentRepository.save(new NormativeDocument(projectId, name));
    }

    @Transactional
    public Optional<NormativeDocument> registerUpload(Long documentId, String fileName,
                                                      String storedFileName, Long uploadedByUserId) {
        return documentRepository.findById(documentId).map(document -> {
            document.markAsUploaded();
            var savedDocument = documentRepository.save(document);
            versionRepository.save(new DocumentVersion(
                    savedDocument.getId(),
                    savedDocument.getCurrentVersion(),
                    fileName,
                    storedFileName,
                    uploadedByUserId));
            return savedDocument;
        });
    }
}
