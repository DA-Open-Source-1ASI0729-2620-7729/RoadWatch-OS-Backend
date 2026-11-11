package com.vianexo.roadwatch.subscription.interfaces.rest.resources;

import com.vianexo.roadwatch.subscription.domain.model.valueobjects.PlanType;

public record PlanResource(
        Long id,
        PlanType name,
        String description,
        Integer maxProjects,
        Integer maxUsers,
        boolean active) {
}
