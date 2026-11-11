package com.vianexo.roadwatch.projectmanagement.application.acl;

import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectContextFacadeImplTest {

    @Test
    void exposesProjectNameAndMonitoringPointIds() {
        var projectQueryService = mock(ProjectQueryService.class);
        var pointQueryService = mock(MonitoringPointQueryService.class);
        var facade = new ProjectContextFacadeImpl(projectQueryService, pointQueryService);
        var project = new Project(10L, "North Road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        var point = new MonitoringPoint(2L, 10L, "North point", -12.0, -77.0, 1L, true);
        when(projectQueryService.getProjectById(10L)).thenReturn(project);
        when(pointQueryService.getPointsByProjectId(10L)).thenReturn(List.of(point));

        assertEquals("North Road", facade.findProjectName(10L));
        assertEquals(List.of(2L), facade.findMonitoringPointIds(10L));
    }
}
