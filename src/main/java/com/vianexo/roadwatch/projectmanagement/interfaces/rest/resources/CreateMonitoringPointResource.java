package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMonitoringPointResource(
        @NotBlank String name,
        @NotNull Double latitude,
        @NotNull Double longitude,
        Long normativeThresholdId
) {
}
