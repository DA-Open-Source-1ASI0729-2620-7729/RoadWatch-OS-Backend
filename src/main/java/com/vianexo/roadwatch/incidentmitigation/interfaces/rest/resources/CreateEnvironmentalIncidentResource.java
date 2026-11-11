package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEnvironmentalIncidentResource(
        @NotNull @Positive Long measurementId,
        @NotBlank String title,
        String description,
        @NotNull IncidentSeverity severity
) {
}
