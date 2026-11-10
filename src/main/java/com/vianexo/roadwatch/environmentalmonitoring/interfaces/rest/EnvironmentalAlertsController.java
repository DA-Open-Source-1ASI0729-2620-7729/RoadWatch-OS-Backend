package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.AcknowledgeEnvironmentalAlertCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.ResolveEnvironmentalAlertCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetAllEnvironmentalAlertsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalAlertsByProjectIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetRecentEnvironmentalAlertsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalAlertCommandService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalAlertQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.EnvironmentalAlertResource;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform.EnvironmentalAlertResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/environmental-alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Environmental Alerts", description = "Environmental Alert Monitoring Endpoints")
public class EnvironmentalAlertsController {

    private final EnvironmentalAlertCommandService commandService;
    private final EnvironmentalAlertQueryService queryService;

    public EnvironmentalAlertsController(
            EnvironmentalAlertCommandService commandService,
            EnvironmentalAlertQueryService queryService
    ) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get environmental alerts with optional projectId filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environmental alerts retrieved")
    })
    public ResponseEntity<List<EnvironmentalAlertResource>> getAlerts(
            @RequestParam(required = false) Long projectId
    ) {
        var alerts = queryService.handle(new GetAllEnvironmentalAlertsQuery());

        if (projectId != null) {
            alerts = queryService.handle(new GetEnvironmentalAlertsByProjectIdQuery(projectId));
        }

        var resources = alerts.stream()
                .map(EnvironmentalAlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recent environmental alerts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recent environmental alerts retrieved")
    })
    public ResponseEntity<List<EnvironmentalAlertResource>> getRecentAlerts() {
        var alerts = queryService.handle(new GetRecentEnvironmentalAlertsQuery());
        var resources = alerts.stream()
                .map(EnvironmentalAlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PatchMapping("/{alertId}/acknowledgements")
    @Operation(summary = "Acknowledge an environmental alert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert acknowledged"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<EnvironmentalAlertResource> acknowledgeAlert(@PathVariable Long alertId) {
        var updated = commandService.handle(new AcknowledgeEnvironmentalAlertCommand(alertId));
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = EnvironmentalAlertResourceFromEntityAssembler.toResourceFromEntity(updated.get());
        return ResponseEntity.ok(resource);
    }

    @PatchMapping("/{alertId}/resolutions")
    @Operation(summary = "Resolve an environmental alert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert resolved"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<EnvironmentalAlertResource> resolveAlert(@PathVariable Long alertId) {
        var updated = commandService.handle(new ResolveEnvironmentalAlertCommand(alertId));
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = EnvironmentalAlertResourceFromEntityAssembler.toResourceFromEntity(updated.get());
        return ResponseEntity.ok(resource);
    }
}
