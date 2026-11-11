package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateProjectResource(
        @NotBlank String name,
        String description
) {
}
