package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.EnvironmentalKpiResource;

public final class EnvironmentalKpiResourceFromKpiAssembler {

    private EnvironmentalKpiResourceFromKpiAssembler() {
    }

    public static EnvironmentalKpiResource toResourceFrom(EnvironmentalKpi kpi) {
        return new EnvironmentalKpiResource(
                kpi.code(),
                kpi.metricName(),
                kpi.value(),
                kpi.unit(),
                kpi.calculatedAt());
    }
}