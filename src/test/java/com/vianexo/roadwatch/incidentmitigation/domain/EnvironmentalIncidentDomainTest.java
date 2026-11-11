package com.vianexo.roadwatch.incidentmitigation.domain;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentalIncidentDomainTest {

    @Test
    void newIncidentStartsOpen() {
        var incident = new EnvironmentalIncident(10L, "Noise above limit", null, IncidentSeverity.HIGH);

        assertEquals(IncidentStatus.OPEN, incident.getStatus());
        assertEquals(IncidentSeverity.HIGH, incident.getSeverity());
    }

    @Test
    void incidentFollowsKanbanTransitions() {
        var incident = new EnvironmentalIncident(10L, "Dust emission", null, IncidentSeverity.MEDIUM);

        incident.changeStatus(IncidentStatus.IN_PROGRESS);
        incident.changeStatus(IncidentStatus.RESOLVED);
        incident.close();

        assertTrue(incident.isClosed());
        assertThrows(IllegalStateException.class, () -> incident.changeStatus(IncidentStatus.IN_PROGRESS));
        assertThrows(IllegalStateException.class, () -> incident.changeSeverity(IncidentSeverity.LOW));
    }

    @Test
    void incidentCannotBeClosedBeforeBeingResolved() {
        var incident = new EnvironmentalIncident(10L, "Water turbidity", null, IncidentSeverity.CRITICAL);

        assertThrows(IllegalStateException.class, incident::close);
    }

    @Test
    void overdueIncidentCanBeResumed() {
        var incident = new EnvironmentalIncident(10L, "Water turbidity", null, IncidentSeverity.CRITICAL);
        incident.markOverdue();
        incident.changeStatus(IncidentStatus.IN_PROGRESS);

        assertEquals(IncidentStatus.IN_PROGRESS, incident.getStatus());
    }

    @Test
    void incidentRequiresMeasurementTitleAndSeverity() {
        assertThrows(IllegalArgumentException.class,
                () -> new EnvironmentalIncident(null, "Title", null, IncidentSeverity.LOW));
        assertThrows(IllegalArgumentException.class,
                () -> new EnvironmentalIncident(1L, " ", null, IncidentSeverity.LOW));
        assertThrows(NullPointerException.class, () -> new EnvironmentalIncident(1L, "Title", null, null));
    }

    @Test
    void mitigationActionCanBeCompletedOnlyOnce() {
        var action = new MitigationAction(1L, "Install noise barriers", 5L);
        action.complete();

        assertTrue(action.isCompleted());
        assertThrows(IllegalStateException.class, action::complete);
    }

    @Test
    void commentRequiresAuthorAndContent() {
        assertThrows(IllegalArgumentException.class, () -> new IncidentComment(1L, null, "Checked"));
        assertThrows(IllegalArgumentException.class, () -> new IncidentComment(1L, 3L, " "));
    }
}
