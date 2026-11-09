package com.vianexo.roadwatch.documentevidence.interfaces.rest.resources;

import com.vianexo.roadwatch.documentevidence.domain.model.valueobjects.DocumentStatus;

import java.time.Instant;

public record NormativeDocumentResource (
  Long id,
  Long projectId,
  String name,
  DocumentStatus status,
  Integer currentVersion,
  Instant createdAt)
{}
