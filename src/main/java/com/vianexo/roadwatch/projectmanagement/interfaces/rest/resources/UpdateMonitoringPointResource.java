package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record UpdateMonitoringPointResource(
        @NotNull Double latitude,
        @NotNull Double longitude,
        Long normativeThresholdId
) {
}
