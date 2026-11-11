package com.vianexo.roadwatch.incidentmitigation.application.events;

public record EnvironmentalAlertGeneratedEvent(Long measurementId, String title, String description, String severity) {
    public EnvironmentalAlertGeneratedEvent {
        if (measurementId == null || measurementId <= 0) {
            throw new IllegalArgumentException("Measurement id must be positive");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Incident title is required");
        }
        if (severity == null || severity.isBlank()) {
            throw new IllegalArgumentException("Incident severity is required");
        }
    }
}
