package com.vianexo.roadwatch.projectmanagement.interfaces.rest.transform;

import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.ProjectResource;

public final class ProjectResourceFromEntityAssembler {

    private ProjectResourceFromEntityAssembler() {
    }

    public static ProjectResource toResourceFromEntity(Project project) {
        return new ProjectResource(project.getId(), project.getName(), project.getDescription(),
                project.getLocation(), project.getStatus(), project.getResponsibleUserId(),
                project.getCreatedAt(), project.getUpdatedAt());
    }
}
