package com.vianexo.roadwatch.incidentmitigation.application;

import com.vianexo.roadwatch.incidentmitigation.application.acl.EvidenceClient;
import com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices.EnvironmentalIncidentCommandServiceImpl;
import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentStatusCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentStatusHistoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EvidenceValidationTest {

    @Test
    void closingAnIncidentRequiresEvidence() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var history = mock(IncidentStatusHistoryRepository.class);
        var evidenceClient = mock(EvidenceClient.class);
        var incident = EnvironmentalIncident.reconstitute(1L, 10L, "Noise alert", null,
                IncidentStatus.RESOLVED, IncidentSeverity.HIGH, null, null);
        when(incidents.findById(1L)).thenReturn(Optional.of(incident));
        when(evidenceClient.hasEvidenceForIncident(1L)).thenReturn(false);
        var service = new EnvironmentalIncidentCommandServiceImpl(incidents, history, Optional.of(evidenceClient));

        assertThrows(IllegalStateException.class,
                () -> service.handle(new ChangeIncidentStatusCommand(1L, IncidentStatus.CLOSED, 7L)));

        verify(incidents, never()).save(any(EnvironmentalIncident.class));
    }

    @Test
    void incidentWithEvidenceCanBeClosed() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var history = mock(IncidentStatusHistoryRepository.class);
        var evidenceClient = mock(EvidenceClient.class);
        var incident = EnvironmentalIncident.reconstitute(1L, 10L, "Noise alert", null,
                IncidentStatus.RESOLVED, IncidentSeverity.HIGH, null, null);
        when(incidents.findById(1L)).thenReturn(Optional.of(incident));
        when(evidenceClient.hasEvidenceForIncident(1L)).thenReturn(true);
        when(incidents.save(any(EnvironmentalIncident.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var service = new EnvironmentalIncidentCommandServiceImpl(incidents, history, Optional.of(evidenceClient));

        var closed = service.handle(new ChangeIncidentStatusCommand(1L, IncidentStatus.CLOSED, 7L));

        assertEquals(IncidentStatus.CLOSED, closed.getStatus());
        verify(history).save(any(IncidentStatusHistory.class));
    }
}
