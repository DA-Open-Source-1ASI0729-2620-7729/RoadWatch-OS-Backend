package com.vianexo.roadwatch.projectmanagement.application;

import com.vianexo.roadwatch.projectmanagement.application.commandservices.MonitoringPointCommandService;
import com.vianexo.roadwatch.projectmanagement.application.commandservices.ProjectCommandService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.MonitoringPointRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectManagementServicesTest {

    @Test
    void projectCommandsPersistChanges() {
        var repository = mock(ProjectRepository.class);
        var project = new Project(1L, "Road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        when(repository.findById(1L)).thenReturn(Optional.of(project));
        when(repository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var service = new ProjectCommandService(repository);

        service.assignResponsible(1L, 7L);
        service.archiveProject(1L);

        assertEquals(7L, project.getResponsibleUserId());
        assertEquals(ProjectStatus.ARCHIVED, project.getStatus());
        verify(repository, org.mockito.Mockito.times(2)).save(project);
    }

    @Test
    void projectQueriesUseStatusAndReportMissingId() {
        var repository = mock(ProjectRepository.class);
        var project = new Project(1L, "Road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        when(repository.findAllByStatus(ProjectStatus.ACTIVE)).thenReturn(List.of(project));
        var service = new ProjectQueryService(repository);

        assertEquals(List.of(project), service.getProjectsByStatus(ProjectStatus.ACTIVE));
        assertThrows(NoSuchElementException.class, () -> service.getProjectById(2L));
    }

    @Test
    void pointCommandsRequireAnActiveProject() {
        var projects = mock(ProjectRepository.class);
        var points = mock(MonitoringPointRepository.class);
        var archivedProject = new Project(1L, "Road", null, "Lima", ProjectStatus.ARCHIVED,
                null, null, null);
        when(projects.findById(1L)).thenReturn(Optional.of(archivedProject));
        var service = new MonitoringPointCommandService(points, projects);

        assertThrows(IllegalStateException.class,
                () -> service.createPoint(1L, "Air", -12.1, -77.0, null));
        verify(points, never()).save(any(MonitoringPoint.class));
    }

    @Test
    void pointQueriesDoNotExposePointsFromAnotherProject() {
        var projects = mock(ProjectRepository.class);
        var points = mock(MonitoringPointRepository.class);
        var project = new Project(1L, "Road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        var otherPoint = new MonitoringPoint(9L, 2L, "Air", -12.1, -77.0, null, true);
        when(projects.findById(1L)).thenReturn(Optional.of(project));
        when(points.findById(9L)).thenReturn(Optional.of(otherPoint));
        var service = new MonitoringPointQueryService(points, projects);

        assertThrows(NoSuchElementException.class, () -> service.getPointById(1L, 9L));
    }
}
