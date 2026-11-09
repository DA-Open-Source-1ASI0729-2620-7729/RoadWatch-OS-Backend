package com.vianexo.roadwatch.projectmanagement.application.commandservices;

import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ProjectCommandService {

    private final ProjectRepository projectRepository;

    public ProjectCommandService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String name, String description, String location) {
        return projectRepository.save(new Project(name, description, location));
    }

    public Project updateProject(Long projectId, String name, String description) {
        Project project = findProject(projectId);
        project.update(name, description);
        return projectRepository.save(project);
    }

    public Project assignResponsible(Long projectId, Long userId) {
        Project project = findProject(projectId);
        project.assignResponsible(userId);
        return projectRepository.save(project);
    }

    public Project archiveProject(Long projectId) {
        Project project = findProject(projectId);
        project.archive();
        return projectRepository.save(project);
    }

    private Project findProject(Long projectId) {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("Project id must be positive");
        }
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + projectId));
    }
}
