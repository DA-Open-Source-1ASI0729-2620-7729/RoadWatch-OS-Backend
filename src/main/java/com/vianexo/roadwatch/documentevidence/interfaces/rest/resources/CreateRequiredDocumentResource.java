package com.vianexo.roadwatch.documentevidence.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateRequiredDocumentResource(
        @NotNull Long projectId,
        @NotNull String milestoneName,
        @NotNull String documentName,
        boolean mandatory,
        LocalDate dueDate
) {
}
