package com.vianexo.roadwatch.subscription.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanPersistenceRepository extends JpaRepository<Plan, Long> {
}
