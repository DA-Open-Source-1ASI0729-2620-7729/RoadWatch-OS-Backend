package com.vianexo.roadwatch.documentevidence.application.events;

public record DocumentVersionUploadedEvent(Long projectId) {

    public DocumentVersionUploadedEvent {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("Project id must be positive");
        }
    }
}
