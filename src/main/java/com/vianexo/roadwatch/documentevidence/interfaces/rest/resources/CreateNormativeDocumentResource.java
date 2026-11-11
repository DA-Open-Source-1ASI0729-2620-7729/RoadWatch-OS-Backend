package com.vianexo.roadwatch.documentevidence.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNormativeDocumentResource(
        @NotNull Long projectId,
        @NotBlank String name
) {
}
