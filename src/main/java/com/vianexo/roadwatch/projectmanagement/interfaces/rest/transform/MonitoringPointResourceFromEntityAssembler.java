package com.vianexo.roadwatch.projectmanagement.interfaces.rest.transform;

import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.MonitoringPointResource;

public final class MonitoringPointResourceFromEntityAssembler {

    private MonitoringPointResourceFromEntityAssembler() {
    }

    public static MonitoringPointResource toResourceFromEntity(MonitoringPoint point) {
        return new MonitoringPointResource(point.getId(), point.getProjectId(), point.getName(),
                point.getLatitude(), point.getLongitude(), point.getNormativeThresholdId(), point.isActive());
    }
}
