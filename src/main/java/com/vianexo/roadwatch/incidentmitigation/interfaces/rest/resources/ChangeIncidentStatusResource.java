package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangeIncidentStatusResource(
        @NotNull IncidentStatus status,
        @NotNull @Positive Long changedByUserId
) {
}
