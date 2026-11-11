package com.vianexo.roadwatch.projectmanagement.domain.model.aggregates;

import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Project {

    private final Long id;
    private String name;
    private String description;
    private final String location;
    private ProjectStatus status;
    private Long responsibleUserId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Project(String name, String description, String location) {
        this(null, name, description, location, ProjectStatus.ACTIVE, null, null, null);
    }

    public Project(Long id, String name, String description, String location, ProjectStatus status,
                   Long responsibleUserId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("Project id must be positive");
        }
        this.id = id;
        this.name = requiredText(name, "Project name");
        this.description = description;
        this.location = requiredText(location, "Project location");
        this.status = Objects.requireNonNull(status, "Project status is required");
        validateUserId(responsibleUserId);
        this.responsibleUserId = responsibleUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String name, String description) {
        requireActive();
        this.name = requiredText(name, "Project name");
        this.description = description;
    }

    public void assignResponsible(Long userId) {
        requireActive();
        validateUserId(userId);
        this.responsibleUserId = userId;
    }

    public void archive() {
        requireActive();
        this.status = ProjectStatus.ARCHIVED;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Long getResponsibleUserId() {
        return responsibleUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void requireActive() {
        if (status != ProjectStatus.ACTIVE) {
            throw new IllegalStateException("Archived projects cannot be changed");
        }
    }

    private static String requiredText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return value.trim();
    }

    private static void validateUserId(Long userId) {
        if (userId != null && userId <= 0) {
            throw new IllegalArgumentException("Responsible user id must be positive");
        }
    }
}
