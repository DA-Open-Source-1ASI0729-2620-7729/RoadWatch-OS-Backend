package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;
import com.vianexo.roadwatch.documentevidence.domain.repositories.RequiredDocumentRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequiredDocumentServiceTest {

    private final RequiredDocumentRepository requiredDocumentRepository = mock(RequiredDocumentRepository.class);
    private final RequiredDocumentService requiredDocumentService = new RequiredDocumentService(requiredDocumentRepository);

    @Test
    void definesRequirement() {
        when(requiredDocumentRepository.save(any(RequiredDocument.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var document = requiredDocumentService.defineRequirement(
                10L, "Construction", "Environmental plan", true, LocalDate.now());

        assertEquals(10L, document.getProjectId());
        assertTrue(document.isMandatory());
    }

    @Test
    void validatesMilestoneWithRequirements() {
        when(requiredDocumentRepository.findByProjectIdAndMilestoneName(10L, "Construction"))
                .thenReturn(List.of(new RequiredDocument(10L, "Construction", "Environmental plan", true, null)));

        assertTrue(requiredDocumentService.validateMilestone(10L, "Construction"));
    }

    @Test
    void doesNotValidateMilestoneWithoutRequirements() {
        when(requiredDocumentRepository.findByProjectIdAndMilestoneName(10L, "Construction"))
                .thenReturn(List.of());

        assertFalse(requiredDocumentService.validateMilestone(10L, "Construction"));
    }
}
