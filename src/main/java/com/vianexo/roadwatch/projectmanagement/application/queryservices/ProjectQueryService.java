package com.vianexo.roadwatch.projectmanagement.application.queryservices;

import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ProjectQueryService {

    private final ProjectRepository projectRepository;

    public ProjectQueryService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProjectById(Long projectId) {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("Project id must be positive");
        }
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + projectId));
    }

    public List<Project> getProjectsByStatus(ProjectStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Project status is required");
        }
        return projectRepository.findAllByStatus(status);
    }

    public PageResult<Project> getProjectsByStatus(ProjectStatus status, int page, int size) {
        if (status == null) {
            throw new IllegalArgumentException("Project status is required");
        }
        validatePage(page, size);
        return projectRepository.findAllByStatus(status, page, size);
    }

    private static void validatePage(int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException("Page must be nonnegative and size must be between 1 and 100");
        }
    }
}
