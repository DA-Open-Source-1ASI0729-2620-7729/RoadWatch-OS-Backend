package com.vianexo.roadwatch.projectmanagement.application;

import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.MonitoringPointRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectPaginationTest {

    @Test
    void projectQueryReturnsPageMetadata() {
        var repository = mock(ProjectRepository.class);
        var project = new Project(2L, "Road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        when(repository.findAllByStatus(ProjectStatus.ACTIVE, 1, 20))
                .thenReturn(new PageResult<>(List.of(project), 1, 20, 21, 2));

        var result = new ProjectQueryService(repository).getProjectsByStatus(ProjectStatus.ACTIVE, 1, 20);

        assertEquals(List.of(project), result.content());
        assertEquals(21, result.totalElements());
        assertEquals(2, result.totalPages());
    }

    @Test
    void paginationRejectsInvalidSize() {
        var service = new ProjectQueryService(mock(ProjectRepository.class));

        assertThrows(IllegalArgumentException.class,
                () -> service.getProjectsByStatus(ProjectStatus.ACTIVE, 0, 101));
        assertThrows(IllegalArgumentException.class,
                () -> service.getProjectsByStatus(ProjectStatus.ACTIVE, -1, 20));
    }

    @Test
    void monitoringPointQueryPaginatesWithinItsProject() {
        var projects = mock(ProjectRepository.class);
        var points = mock(MonitoringPointRepository.class);
        var project = new Project(1L, "Road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        var point = new MonitoringPoint(3L, 1L, "Air", -12, -77, null, true);
        when(projects.findById(1L)).thenReturn(Optional.of(project));
        when(points.findByProjectId(1L, 0, 10))
                .thenReturn(new PageResult<>(List.of(point), 0, 10, 1, 1));

        var result = new MonitoringPointQueryService(points, projects).getPointsByProjectId(1L, 0, 10);

        assertEquals(List.of(point), result.content());
        assertEquals(1, result.totalElements());
    }
}
