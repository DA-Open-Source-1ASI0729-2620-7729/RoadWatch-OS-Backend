package com.vianexo.roadwatch.subscription.interfaces.rest.controllers;

import com.vianexo.roadwatch.subscription.application.services.PlanService;
import com.vianexo.roadwatch.subscription.interfaces.rest.resources.PlanResource;
import com.vianexo.roadwatch.subscription.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @Operation(summary = "Get available plans")
    @GetMapping
    public ResponseEntity<List<PlanResource>> getPlans() {
        var resources = planService.findAll().stream()
                .map(PlanResourceFromEntityAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get plan by identifier")
    @GetMapping("/{planId}")
    public ResponseEntity<PlanResource> getPlanById(@PathVariable Long planId) {
        return ResponseEntity.ok(PlanResourceFromEntityAssembler.toResourceFrom(planService.findById(planId)));
    }
}
