package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.application.events.DocumentVersionUploadedEvent;
import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import com.vianexo.roadwatch.documentevidence.domain.model.entities.DocumentVersion;
import com.vianexo.roadwatch.documentevidence.domain.repositories.DocumentVersionRepository;
import com.vianexo.roadwatch.documentevidence.domain.repositories.NormativeDocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NormativeDocumentService {

    private final NormativeDocumentRepository documentRepository;
    private final DocumentVersionRepository versionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public NormativeDocumentService(NormativeDocumentRepository documentRepository,
                                    DocumentVersionRepository versionRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.documentRepository = documentRepository;
        this.versionRepository = versionRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<NormativeDocument> findAll() {
        return documentRepository.findAll();
    }

    public Optional<NormativeDocument> findById(Long id) {
        return documentRepository.findById(id);
    }

    public NormativeDocument createDocument(Long projectId, String name) {
        return documentRepository.save(new NormativeDocument(projectId, name));
    }

    @Transactional
    public Optional<NormativeDocument> uploadNewVersion(Long documentId, String fileName,
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
            eventPublisher.publishEvent(new DocumentVersionUploadedEvent(savedDocument.getProjectId()));
            return savedDocument;
        });
    }

    public Optional<NormativeDocument> markDocumentMissing(Long documentId) {
        return documentRepository.findById(documentId).map(document -> {
            document.markAsMissing();
            return documentRepository.save(document);
        });
    }

    public List<NormativeDocument> findByProject(Long projectId) {
        return documentRepository.findByProjectId(projectId);
    }

    public Optional<DocumentVersion> findCurrentVersion(Long documentId) {
        return versionRepository.findByDocumentId(documentId).stream()
                .max(Comparator.comparing(DocumentVersion::getVersionNumber));
    }
}
