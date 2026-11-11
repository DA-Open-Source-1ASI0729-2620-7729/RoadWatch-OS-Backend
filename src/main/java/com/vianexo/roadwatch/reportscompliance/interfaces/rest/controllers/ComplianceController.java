package com.vianexo.roadwatch.reportscompliance.interfaces.rest.controllers;

import com.vianexo.roadwatch.reportscompliance.application.services.ComplianceService;
import com.vianexo.roadwatch.reportscompliance.application.services.KpiService;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.ComplianceSummaryResource;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.EnvironmentalKpiResource;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.ProjectReportResource;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform.ComplianceSummaryResourceFromSummaryAssembler;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform.EnvironmentalKpiResourceFromKpiAssembler;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform.ProjectReportResourceFromProjectComplianceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compliance")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class ComplianceController {

    private final ComplianceService complianceService;
    private final KpiService kpiService;

    public ComplianceController(ComplianceService complianceService, KpiService kpiService) {
        this.complianceService = complianceService;
        this.kpiService = kpiService;
    }

    @Operation(summary = "Get compliance summary")
    @GetMapping("/summary")
    public ResponseEntity<ComplianceSummaryResource> getSummary() {
        return ResponseEntity.ok(
                ComplianceSummaryResourceFromSummaryAssembler.toResourceFrom(complianceService.getSummary()));
    }

    @Operation(summary = "Get environmental KPIs")
    @GetMapping("/kpis")
    public ResponseEntity<List<EnvironmentalKpiResource>> getKpis() {
        var resources = kpiService.findAll().stream()
                .map(EnvironmentalKpiResourceFromKpiAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get compliance by project")
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectReportResource>> getProjects() {
        var resources = complianceService.getProjects().stream()
                .map(ProjectReportResourceFromProjectComplianceAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
