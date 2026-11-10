package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RegisterMitigationActionResource(
        @NotBlank String description,
        @Positive Long responsibleUserId
) {
}
