package com.vianexo.roadwatch.reportscompliance.infrastructure.providers;

import com.vianexo.roadwatch.environmentalmonitoring.interfaces.acl.EnvironmentalMonitoringContextFacade;
import com.vianexo.roadwatch.incidentmitigation.interfaces.acl.IncidentContextFacade;
import com.vianexo.roadwatch.projectmanagement.interfaces.acl.ProjectContextFacade;
import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IntegratedComplianceDataProvider implements ComplianceDataProvider {

    private final ProjectContextFacade projectContextFacade;
    private final EnvironmentalMonitoringContextFacade environmentalMonitoringContextFacade;
    private final IncidentContextFacade incidentContextFacade;

    public IntegratedComplianceDataProvider(ProjectContextFacade projectContextFacade,
                                            EnvironmentalMonitoringContextFacade environmentalMonitoringContextFacade,
                                            IncidentContextFacade incidentContextFacade) {
        this.projectContextFacade = projectContextFacade;
        this.environmentalMonitoringContextFacade = environmentalMonitoringContextFacade;
        this.incidentContextFacade = incidentContextFacade;
    }

    @Override
    public String findProjectName(Long projectId) {
        return projectContextFacade.findProjectName(projectId);
    }

    @Override
    public String findContractor(Long projectId) {
        return "Sin información";
    }

    @Override
    public int countOpenIncidents(Long projectId) {
        return incidentContextFacade.countOpenIncidents(measurementIdsFor(projectId));
    }

    @Override
    public int countCriticalIncidents(Long projectId) {
        return incidentContextFacade.countCriticalIncidents(measurementIdsFor(projectId));
    }

    @Override
    public Double averageResolutionHours(Long projectId) {
        return incidentContextFacade.averageResolutionHours(measurementIdsFor(projectId));
    }

    @Override
    public int countCompliantMeasurements(Long projectId) {
        return environmentalMonitoringContextFacade.countCompliantMeasurements(monitoringPointIdsFor(projectId));
    }

    @Override
    public int countNonCompliantMeasurements(Long projectId) {
        return environmentalMonitoringContextFacade.countNonCompliantMeasurements(monitoringPointIdsFor(projectId));
    }

    private List<Long> monitoringPointIdsFor(Long projectId) {
        return projectContextFacade.findMonitoringPointIds(projectId);
    }

    private List<Long> measurementIdsFor(Long projectId) {
        return environmentalMonitoringContextFacade.findMeasurementIdsByMonitoringPointIds(
                monitoringPointIdsFor(projectId));
    }
}
