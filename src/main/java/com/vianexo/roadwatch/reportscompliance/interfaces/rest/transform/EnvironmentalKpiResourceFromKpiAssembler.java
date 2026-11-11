package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.EnvironmentalKpiResource;

public final class EnvironmentalKpiResourceFromKpiAssembler {

    private EnvironmentalKpiResourceFromKpiAssembler() {
    }

    public static EnvironmentalKpiResource toResourceFrom(EnvironmentalKpi kpi) {
        return new EnvironmentalKpiResource(
                kpi.getId(),
                kpi.getProjectId(),
                kpi.getCompliancePercentage(),
                kpi.getAverageResolutionTime(),
                kpi.getOpenIncidents(),
                kpi.getCriticalIncidents(),
                kpi.getCalculatedAt());
    }
}
