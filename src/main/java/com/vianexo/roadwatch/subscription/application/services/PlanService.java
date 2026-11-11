package com.vianexo.roadwatch.subscription.application.services;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Plan;
import com.vianexo.roadwatch.subscription.domain.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Plan findById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: %s".formatted(id)));
    }
}
