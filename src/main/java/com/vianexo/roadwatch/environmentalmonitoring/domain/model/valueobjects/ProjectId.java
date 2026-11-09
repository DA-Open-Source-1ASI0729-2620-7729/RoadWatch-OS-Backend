package com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProjectId(Long projectId) {
    public ProjectId {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("projectId must be a positive number");
        }
    }
}