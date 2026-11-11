package com.vianexo.roadwatch.documentevidence.interfaces.rest.resources;

import java.time.Instant;

public record EvidenceResource(
        Long id,
        Long incidentId,
        Long measurementId,
        String fileName,
        String fileUrl,
        String fileType,
        Long uploadedByUserId,
        Instant uploadedAt
)
{}
