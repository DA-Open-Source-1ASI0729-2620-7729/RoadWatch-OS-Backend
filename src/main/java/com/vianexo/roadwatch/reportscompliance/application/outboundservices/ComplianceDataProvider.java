package com.vianexo.roadwatch.reportscompliance.application.outboundservices;


public interface ComplianceDataProvider {

    String findProjectName(Long projectId);

    String findContractor(Long projectId);
    int countOpenIncidents(Long projectId);

    int countCriticalIncidents(Long projectId);

    Double averageResolutionHours(Long projectId);

    int countCompliantMeasurements(Long projectId);

    int countNonCompliantMeasurements(Long projectId);
}
