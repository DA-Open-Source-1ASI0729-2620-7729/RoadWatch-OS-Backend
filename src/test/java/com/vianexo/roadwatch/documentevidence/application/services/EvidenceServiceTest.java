package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;
import com.vianexo.roadwatch.documentevidence.domain.repositories.EvidenceRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EvidenceServiceTest {

    private final EvidenceRepository evidenceRepository = mock(EvidenceRepository.class);
    private final EvidenceService evidenceService = new EvidenceService(evidenceRepository);

    @Test
    void findsEvidenceByIncident() {
        var evidence = new Evidence(10L, 20L, "evidence.pdf", "application/pdf", "stored-file", 30L);
        when(evidenceRepository.findByIncidentId(10L)).thenReturn(List.of(evidence));

        var result = evidenceService.findByIncident(10L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getIncidentId());
    }

    @Test
    void findsEvidenceByMeasurement() {
        var evidence = new Evidence(10L, 20L, "evidence.pdf", "application/pdf", "stored-file", 30L);
        when(evidenceRepository.findByMeasurementId(20L)).thenReturn(List.of(evidence));

        var result = evidenceService.findByMeasurement(20L);

        assertEquals(1, result.size());
        assertEquals(20L, result.get(0).getMeasurementId());
    }

    @Test
    void checksEvidenceForIncident() {
        when(evidenceRepository.hasEvidenceForIncident(10L)).thenReturn(true);

        assertTrue(evidenceService.hasEvidenceForIncident(10L));
    }
}
