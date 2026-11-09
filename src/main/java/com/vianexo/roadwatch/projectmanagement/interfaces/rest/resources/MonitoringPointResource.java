package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

public record MonitoringPointResource(
        Long id,
        Long projectId,
        String name,
        double latitude,
        double longitude,
        Long normativeThresholdId,
        boolean active
) {
}
