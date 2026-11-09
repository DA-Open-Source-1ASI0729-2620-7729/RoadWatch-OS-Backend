package com.vianexo.roadwatch.documentevidence.interfaces.acl;

import com.vianexo.roadwatch.documentevidence.application.services.NormativeDocumentService;
import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import com.vianexo.roadwatch.documentevidence.domain.model.valueobjects.DocumentStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentEvidenceContextFacade {

    private final NormativeDocumentService documentService;

    public DocumentEvidenceContextFacade(NormativeDocumentService documentService) {
        this.documentService = documentService;
    }

    public int countRequiredDocuments() {
        return documentService.findAll().size();
    }

    public int countRequiredDocuments(Long projectId) {
        return (int) documentService.findAll().stream()
                .filter(document -> document.getProjectId().equals(projectId))
                .count();
    }

    public int countPendingDocuments() {
        return (int) documentService.findAll().stream()
                .filter(this::isPending)
                .count();
    }

    public int countPendingDocuments(Long projectId) {
        return (int) documentService.findAll().stream()
                .filter(document -> document.getProjectId().equals(projectId))
                .filter(this::isPending)
                .count();
    }

    public List<Long> findProjectIds() {
        return documentService.findAll().stream()
                .map(NormativeDocument::getProjectId)
                .distinct()
                .toList();
    }

    private boolean isPending(NormativeDocument document) {
        return document.getStatus() != DocumentStatus.UPLOADED;
    }
}