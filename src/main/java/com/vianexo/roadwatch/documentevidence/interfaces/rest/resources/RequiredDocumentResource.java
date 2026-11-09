package com.vianexo.roadwatch.documentevidence.interfaces.rest.resources;

import java.time.LocalDate;

public record RequiredDocumentResource(
        Long id,
        Long projectId,
        String milestoneName,
        String documentName,
        boolean mandatory,
        LocalDate dueDate
)
{}
