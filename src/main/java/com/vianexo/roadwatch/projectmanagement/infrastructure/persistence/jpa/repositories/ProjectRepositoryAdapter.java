package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities.ProjectJpaEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryAdapter implements ProjectRepository {

    private final ProjectJpaRepository jpaRepository;

    public ProjectRepositoryAdapter(ProjectJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Project save(Project project) {
        ProjectJpaEntity entity;
        if (project.getId() == null) {
            entity = new ProjectJpaEntity(project.getName(), project.getDescription(), project.getLocation(),
                    project.getStatus(), project.getResponsibleUserId());
        } else {
            entity = jpaRepository.findById(project.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + project.getId()));
            entity.update(project.getName(), project.getDescription(), project.getLocation(),
                    project.getStatus(), project.getResponsibleUserId());
        }
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpaRepository.findById(id).map(ProjectRepositoryAdapter::toDomain);
    }

    @Override
    public List<Project> findAllByStatus(ProjectStatus status) {
        return jpaRepository.findAllByStatus(status).stream()
                .map(ProjectRepositoryAdapter::toDomain)
                .toList();
    }

    @Override
    public PageResult<Project> findAllByStatus(ProjectStatus status, int page, int size) {
        var result = jpaRepository.findAllByStatus(status, PageRequest.of(page, size, Sort.by("id")));
        return new PageResult<>(result.getContent().stream().map(ProjectRepositoryAdapter::toDomain).toList(),
                page, size, result.getTotalElements(), result.getTotalPages());
    }

    @Override
    public PageResult<Project> findByNameContaining(ProjectStatus status, String name, int page, int size) {
        var result = jpaRepository.findByStatusAndNameContainingIgnoreCase(status, name,
                PageRequest.of(page, size, Sort.by("id")));
        return new PageResult<>(result.getContent().stream().map(ProjectRepositoryAdapter::toDomain).toList(),
                page, size, result.getTotalElements(), result.getTotalPages());
    }

    private static Project toDomain(ProjectJpaEntity entity) {
        return new Project(entity.getId(), entity.getName(), entity.getDescription(), entity.getLocation(),
                entity.getStatus(), entity.getResponsibleUserId(), toLocalDateTime(entity.getCreatedAt()),
                toLocalDateTime(entity.getUpdatedAt()));
    }

    private static LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }
}
