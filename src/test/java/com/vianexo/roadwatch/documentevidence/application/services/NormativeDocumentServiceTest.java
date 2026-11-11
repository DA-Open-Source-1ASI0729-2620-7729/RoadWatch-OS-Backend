package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.application.events.DocumentVersionUploadedEvent;
import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import com.vianexo.roadwatch.documentevidence.domain.model.valueobjects.DocumentStatus;
import com.vianexo.roadwatch.documentevidence.domain.repositories.DocumentVersionRepository;
import com.vianexo.roadwatch.documentevidence.domain.repositories.NormativeDocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NormativeDocumentServiceTest {

    private final NormativeDocumentRepository documentRepository = mock(NormativeDocumentRepository.class);
    private final DocumentVersionRepository versionRepository = mock(DocumentVersionRepository.class);
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    private final NormativeDocumentService documentService = new NormativeDocumentService(
            documentRepository, versionRepository, eventPublisher);

    @Test
    void createsDocument() {
        when(documentRepository.save(any(NormativeDocument.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var document = documentService.createDocument(10L, "Environmental plan");

        assertEquals(10L, document.getProjectId());
        assertEquals(DocumentStatus.REQUIRED, document.getStatus());
    }

    @Test
    void uploadsNewVersion() {
        var document = new NormativeDocument(10L, "Environmental plan");
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(document)).thenReturn(document);

        var result = documentService.uploadNewVersion(1L, "plan.pdf", "stored-file", 20L);

        assertTrue(result.isPresent());
        assertEquals(DocumentStatus.UPLOADED, result.get().getStatus());
        assertEquals(1, result.get().getCurrentVersion());
        verify(eventPublisher).publishEvent(new DocumentVersionUploadedEvent(10L));
    }

    @Test
    void marksDocumentAsMissing() {
        var document = new NormativeDocument(10L, "Environmental plan");
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(document)).thenReturn(document);

        var result = documentService.markDocumentMissing(1L);

        assertTrue(result.isPresent());
        assertEquals(DocumentStatus.MISSING, result.get().getStatus());
    }

    @Test
    void findsDocumentsByProject() {
        when(documentRepository.findByProjectId(10L))
                .thenReturn(List.of(new NormativeDocument(10L, "Environmental plan")));

        var documents = documentService.findByProject(10L);

        assertEquals(1, documents.size());
        assertEquals(10L, documents.get(0).getProjectId());
    }
}
