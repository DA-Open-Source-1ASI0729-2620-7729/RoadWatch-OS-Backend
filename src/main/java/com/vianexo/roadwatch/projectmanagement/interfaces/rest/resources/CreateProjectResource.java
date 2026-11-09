package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectResource(
        @NotBlank String name,
        String description,
        @NotBlank String location
) {
}
