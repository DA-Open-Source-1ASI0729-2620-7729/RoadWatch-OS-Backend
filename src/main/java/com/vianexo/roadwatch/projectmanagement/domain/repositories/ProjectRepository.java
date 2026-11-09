package com.vianexo.roadwatch.projectmanagement.domain.repositories;

import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAllByStatus(ProjectStatus status);
}
