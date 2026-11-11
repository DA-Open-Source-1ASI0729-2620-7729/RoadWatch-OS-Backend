package com.vianexo.roadwatch.subscription.domain.model.aggregates;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.vianexo.roadwatch.subscription.domain.model.valueobjects.PlanType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "plans")
@NoArgsConstructor
public class Plan extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PlanType name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer maxProjects;

    @Column(nullable = false)
    private Integer maxUsers;

    @Column(nullable = false)
    private boolean active;

    public Plan(PlanType name, String description, Integer maxProjects, Integer maxUsers) {
        this.name = name;
        this.description = description;
        this.maxProjects = maxProjects;
        this.maxUsers = maxUsers;
        this.active = true;
    }

    public void updateLimits(Integer maxProjects, Integer maxUsers) {
        this.maxProjects = maxProjects;
        this.maxUsers = maxUsers;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
