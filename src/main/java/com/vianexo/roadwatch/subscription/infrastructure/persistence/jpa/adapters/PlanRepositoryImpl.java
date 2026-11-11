package com.vianexo.roadwatch.subscription.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Plan;
import com.vianexo.roadwatch.subscription.domain.repositories.PlanRepository;
import com.vianexo.roadwatch.subscription.infrastructure.persistence.jpa.repositories.PlanPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanPersistenceRepository planPersistenceRepository;

    public PlanRepositoryImpl(PlanPersistenceRepository planPersistenceRepository) {
        this.planPersistenceRepository = planPersistenceRepository;
    }

    @Override
    public Plan save(Plan plan) {
        return planPersistenceRepository.save(plan);
    }

    @Override
    public List<Plan> findAll() {
        return planPersistenceRepository.findAll();
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return planPersistenceRepository.findById(id);
    }
}
