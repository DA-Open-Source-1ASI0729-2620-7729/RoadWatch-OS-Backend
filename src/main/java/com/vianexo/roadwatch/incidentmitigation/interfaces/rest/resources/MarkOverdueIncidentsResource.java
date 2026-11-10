package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MarkOverdueIncidentsResource(
        @NotNull @Min(1) Integer maxOpenDays,
        @NotNull @Positive Long changedByUserId
) {
}
