package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities;

import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class ProjectJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    private Long responsibleUserId;

    protected ProjectJpaEntity() {
    }

    public ProjectJpaEntity(String name, String description, String location, ProjectStatus status,
                            Long responsibleUserId) {
        update(name, description, location, status, responsibleUserId);
    }

    public void update(String name, String description, String location, ProjectStatus status,
                       Long responsibleUserId) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.status = status;
        this.responsibleUserId = responsibleUserId;
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
}
