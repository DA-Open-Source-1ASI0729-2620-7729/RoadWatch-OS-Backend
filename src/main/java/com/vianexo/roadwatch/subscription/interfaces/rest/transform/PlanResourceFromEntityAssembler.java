package com.vianexo.roadwatch.subscription.interfaces.rest.transform;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Plan;
import com.vianexo.roadwatch.subscription.interfaces.rest.resources.PlanResource;

public final class PlanResourceFromEntityAssembler {

    private PlanResourceFromEntityAssembler() {
    }

    public static PlanResource toResourceFrom(Plan plan) {
        return new PlanResource(
                plan.getId(),
                plan.getName(),
                plan.getDescription(),
                plan.getMaxProjects(),
                plan.getMaxUsers(),
                plan.isActive());
    }
}
