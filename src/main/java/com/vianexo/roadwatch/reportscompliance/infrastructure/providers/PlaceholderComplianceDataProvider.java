package com.vianexo.roadwatch.reportscompliance.infrastructure.providers;
import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class PlaceholderComplianceDataProvider implements ComplianceDataProvider {

    @Override
    public String findProjectName(Long projectId) {
        return "Proyecto " + projectId;
    }
    @Override
    public String findContractor(Long projectId) {
        return "Sin información";
    }

    @Override
    public int countOpenIncidents(Long projectId) {
        return 0;
    }

    @Override
    public int countCriticalIncidents(Long projectId) {
        return 0;
    }
    @Override
    public Double averageResolutionHours() {
        return null;
    }
}