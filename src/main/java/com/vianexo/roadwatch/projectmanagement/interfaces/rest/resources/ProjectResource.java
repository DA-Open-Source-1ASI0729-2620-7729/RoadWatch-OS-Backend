package com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources;

import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;

import java.time.LocalDateTime;

public record ProjectResource(
        Long id,
        String name,
        String description,
        String location,
        ProjectStatus status,
        Long responsibleUserId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
