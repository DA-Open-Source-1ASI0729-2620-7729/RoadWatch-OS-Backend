package com.vianexo.roadwatch.subscription.domain.repositories;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    List<Plan> findAll();

    Optional<Plan> findById(Long id);
}
