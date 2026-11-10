package com.vianexo.roadwatch.incidentmitigation.application;

import com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices.EnvironmentalIncidentCommandServiceImpl;
import com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices.IncidentCommentCommandServiceImpl;
import com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices.MitigationActionCommandServiceImpl;
import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.AddIncidentCommentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentStatusCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CompleteMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.MarkOverdueIncidentsCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentCommentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentStatusHistoryRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.MitigationActionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IncidentMitigationServicesTest {

    private static EnvironmentalIncident incident(IncidentStatus status) {
        return EnvironmentalIncident.reconstitute(1L, 10L, "Noise above limit", null, status,
                IncidentSeverity.HIGH, null, null);
    }

    @Test
    void statusChangeIsRecordedInHistory() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var history = mock(IncidentStatusHistoryRepository.class);
        when(incidents.findById(1L)).thenReturn(Optional.of(incident(IncidentStatus.OPEN)));
        when(incidents.save(any(EnvironmentalIncident.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var service = new EnvironmentalIncidentCommandServiceImpl(incidents, history);

        service.handle(new ChangeIncidentStatusCommand(1L, IncidentStatus.IN_PROGRESS, 7L));

        var captor = ArgumentCaptor.forClass(IncidentStatusHistory.class);
        verify(history).save(captor.capture());
        assertEquals(IncidentStatus.OPEN, captor.getValue().getPreviousStatus());
        assertEquals(IncidentStatus.IN_PROGRESS, captor.getValue().getNewStatus());
        assertEquals(7L, captor.getValue().getChangedByUserId());
    }

    @Test
    void invalidTransitionIsNotPersisted() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var history = mock(IncidentStatusHistoryRepository.class);
        when(incidents.findById(1L)).thenReturn(Optional.of(incident(IncidentStatus.OPEN)));
        var service = new EnvironmentalIncidentCommandServiceImpl(incidents, history);

        assertThrows(IllegalStateException.class,
                () -> service.handle(new ChangeIncidentStatusCommand(1L, IncidentStatus.CLOSED, 7L)));
        verify(incidents, never()).save(any(EnvironmentalIncident.class));
        verify(history, never()).save(any(IncidentStatusHistory.class));
    }

    @Test
    void overdueCheckMarksOldOpenIncidents() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var history = mock(IncidentStatusHistoryRepository.class);
        when(incidents.findByStatusInAndCreatedAtBefore(anyCollection(), any()))
                .thenReturn(List.of(incident(IncidentStatus.OPEN)));
        when(incidents.save(any(EnvironmentalIncident.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var service = new EnvironmentalIncidentCommandServiceImpl(incidents, history);

        var marked = service.handle(new MarkOverdueIncidentsCommand(7, 1L));

        assertEquals(IncidentStatus.OVERDUE, marked.get(0).getStatus());
        verify(history).save(any(IncidentStatusHistory.class));
    }

    @Test
    void closedIncidentRejectsCommentsAndActions() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var comments = mock(IncidentCommentRepository.class);
        var actions = mock(MitigationActionRepository.class);
        when(incidents.findById(1L)).thenReturn(Optional.of(incident(IncidentStatus.CLOSED)));
        var commentService = new IncidentCommentCommandServiceImpl(comments, incidents);
        var actionService = new MitigationActionCommandServiceImpl(actions, incidents);

        assertThrows(IllegalStateException.class,
                () -> commentService.handle(new AddIncidentCommentCommand(1L, 3L, "Checked on site")));
        assertThrows(IllegalStateException.class,
                () -> actionService.handle(new CompleteMitigationActionCommand(1L, 4L)));
        verify(comments, never()).save(any(IncidentComment.class));
        verify(actions, never()).save(any(MitigationAction.class));
    }

    @Test
    void mitigationActionFromAnotherIncidentIsNotFound() {
        var incidents = mock(EnvironmentalIncidentRepository.class);
        var actions = mock(MitigationActionRepository.class);
        when(incidents.findById(1L)).thenReturn(Optional.of(incident(IncidentStatus.IN_PROGRESS)));
        when(actions.findById(4L)).thenReturn(Optional.of(
                MitigationAction.reconstitute(4L, 2L, "Water the road", null, null, null)));
        var service = new MitigationActionCommandServiceImpl(actions, incidents);

        assertThrows(NoSuchElementException.class,
                () -> service.handle(new CompleteMitigationActionCommand(1L, 4L)));
    }
}
