package com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class EnvironmentalIncident {

    private Long id;
    private final Long measurementId;
    private final String title;
    private final String description;
    private IncidentStatus status;
    private IncidentSeverity severity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EnvironmentalIncident(Long measurementId, String title, String description, IncidentSeverity severity) {
        if (measurementId == null || measurementId <= 0) {
            throw new IllegalArgumentException("Measurement id must be positive");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Incident title is required");
        }
        this.measurementId = measurementId;
        this.title = title.trim();
        this.description = description;
        this.severity = Objects.requireNonNull(severity, "Incident severity is required");
        this.status = IncidentStatus.OPEN;
    }

    public static EnvironmentalIncident reconstitute(Long id, Long measurementId, String title, String description,
                                                     IncidentStatus status, IncidentSeverity severity,
                                                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        var incident = new EnvironmentalIncident(measurementId, title, description, severity);
        incident.id = id;
        incident.status = Objects.requireNonNull(status, "Incident status is required");
        incident.createdAt = createdAt;
        incident.updatedAt = updatedAt;
        return incident;
    }

    public void changeStatus(IncidentStatus newStatus) {
        Objects.requireNonNull(newStatus, "Incident status is required");
        if (!status.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Incident cannot change from %s to %s".formatted(status, newStatus));
        }
        this.status = newStatus;
    }

    public void changeSeverity(IncidentSeverity newSeverity) {
        requireNotClosed();
        this.severity = Objects.requireNonNull(newSeverity, "Incident severity is required");
    }

    public void markOverdue() {
        changeStatus(IncidentStatus.OVERDUE);
    }

    public void close() {
        changeStatus(IncidentStatus.CLOSED);
    }

    public boolean isClosed() {
        return status == IncidentStatus.CLOSED;
    }

    public void requireNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("Closed incidents cannot be changed");
        }
    }
}
