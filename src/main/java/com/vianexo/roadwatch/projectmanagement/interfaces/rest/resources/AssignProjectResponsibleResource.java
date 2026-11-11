package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssignProjectResponsibleResource(
        @NotNull @Positive Long userId
) {
}
