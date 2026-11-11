package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddIncidentCommentResource(
        @NotNull @Positive Long authorUserId,
        @NotBlank String content
) {
}
