package com.vianexo.roadwatch.projectmanagement.domain;

import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProjectDomainTest {

    @Test
    void archivedProjectCannotBeChanged() {
        var project = new Project("Road extension", null, "Lima");
        project.assignResponsible(7L);
        project.archive();

        assertEquals(ProjectStatus.ARCHIVED, project.getStatus());
        assertEquals(7L, project.getResponsibleUserId());
        assertThrows(IllegalStateException.class, () -> project.update("New name", null));
        assertThrows(IllegalStateException.class, () -> project.assignResponsible(8L));
    }

    @Test
    void projectRequiresNameAndLocation() {
        assertThrows(IllegalArgumentException.class, () -> new Project(" ", null, "Lima"));
        assertThrows(IllegalArgumentException.class, () -> new Project("Road", null, " "));
    }

    @Test
    void monitoringPointRejectsInvalidCoordinatesAndExternalId() {
        assertThrows(IllegalArgumentException.class,
                () -> new MonitoringPoint(1L, "Air", 91, -77, null));
        assertThrows(IllegalArgumentException.class,
                () -> new MonitoringPoint(1L, "Air", -12, Double.NaN, null));
        assertThrows(IllegalArgumentException.class,
                () -> new MonitoringPoint(1L, "Air", -12, -77, -1L));
    }

    @Test
    void inactiveMonitoringPointCannotBeChanged() {
        var point = new MonitoringPoint(1L, "Air", -12.1, -77.0, 5L);
        point.deactivate();

        assertFalse(point.isActive());
        assertThrows(IllegalStateException.class, () -> point.updateLocation(-12.2, -77.1));
        assertThrows(IllegalStateException.class, () -> point.updateThreshold(6L));
    }
}
