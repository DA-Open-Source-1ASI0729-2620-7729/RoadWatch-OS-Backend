package com.vianexo.roadwatch.subscription.infrastructure.configuration;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Plan;
import com.vianexo.roadwatch.subscription.domain.model.valueobjects.PlanType;
import com.vianexo.roadwatch.subscription.domain.repositories.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PlanCatalogInitializer implements CommandLineRunner {

    private final PlanRepository planRepository;

    public PlanCatalogInitializer(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void run(String... args) {
        if (!planRepository.findAll().isEmpty()) {
            return;
        }

        planRepository.save(new Plan(PlanType.BASE, "Plan inicial para equipos pequeños", 3, 5));
        planRepository.save(new Plan(PlanType.PROFESSIONAL, "Plan para operaciones en crecimiento", 10, 20));
        planRepository.save(new Plan(PlanType.ENTERPRISE, "Plan para operaciones de mayor escala", 50, 100));
    }
}
