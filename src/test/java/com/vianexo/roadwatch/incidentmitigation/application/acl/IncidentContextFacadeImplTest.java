package com.vianexo.roadwatch.incidentmitigation.application.acl;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetAllEnvironmentalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetCriticalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentStatusHistoryQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentQueryService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IncidentContextFacadeImplTest {

    @Test
    void calculatesIncidentMetricsForMeasurementIds() {
        var queryService = mock(EnvironmentalIncidentQueryService.class);
        var facade = new IncidentContextFacadeImpl(mock(EnvironmentalIncidentCommandService.class), queryService);
        var createdAt = LocalDateTime.now().minusHours(24);
        var openCritical = EnvironmentalIncident.reconstitute(1L, 10L, "Noise alert", null,
                IncidentStatus.OPEN, IncidentSeverity.CRITICAL, createdAt, createdAt);
        var closed = EnvironmentalIncident.reconstitute(2L, 20L, "Water alert", null,
                IncidentStatus.CLOSED, IncidentSeverity.HIGH, createdAt, createdAt.plusHours(24));
        var closedHistory = IncidentStatusHistory.reconstitute(1L, 2L, IncidentStatus.RESOLVED,
                IncidentStatus.CLOSED, 7L, createdAt.plusHours(24));
        when(queryService.handle(new GetAllEnvironmentalIncidentsQuery(null, null)))
                .thenReturn(List.of(openCritical, closed));
        when(queryService.handle(new GetCriticalIncidentsQuery())).thenReturn(List.of(openCritical));
        when(queryService.handle(new GetIncidentStatusHistoryQuery(2L))).thenReturn(List.of(closedHistory));

        assertEquals(1, facade.countOpenIncidents(List.of(10L, 20L)));
        assertEquals(1, facade.countCriticalIncidents(List.of(10L, 20L)));
        assertEquals(24.0, facade.averageResolutionHours(List.of(10L, 20L)));
    }
}
