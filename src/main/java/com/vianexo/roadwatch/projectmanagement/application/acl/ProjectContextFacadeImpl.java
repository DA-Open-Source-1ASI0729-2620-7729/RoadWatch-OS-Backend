package com.vianexo.roadwatch.projectmanagement.application.acl;

import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.interfaces.acl.ProjectContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectContextFacadeImpl implements ProjectContextFacade {

    private final ProjectQueryService projectQueryService;
    private final MonitoringPointQueryService monitoringPointQueryService;

    public ProjectContextFacadeImpl(ProjectQueryService projectQueryService,
                                    MonitoringPointQueryService monitoringPointQueryService) {
        this.projectQueryService = projectQueryService;
        this.monitoringPointQueryService = monitoringPointQueryService;
    }

    @Override
    public String findProjectName(Long projectId) {
        return projectQueryService.getProjectById(projectId).getName();
    }

    @Override
    public List<Long> findMonitoringPointIds(Long projectId) {
        return monitoringPointQueryService.getPointsByProjectId(projectId).stream()
                .map(MonitoringPoint::getId)
                .toList();
    }
}
