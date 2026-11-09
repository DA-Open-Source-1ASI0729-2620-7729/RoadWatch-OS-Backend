package com.vianexo.roadwatch.projectmanagement.interfaces.rest;

import com.vianexo.roadwatch.projectmanagement.application.commandservices.MonitoringPointCommandService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.CreateMonitoringPointResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.MonitoringPointResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.UpdateMonitoringPointResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.transform.MonitoringPointResourceFromEntityAssembler;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/monitoring-points")
@Tag(name = "Project Management", description = "Road project management")
public class MonitoringPointController {

    private final MonitoringPointCommandService commandService;
    private final MonitoringPointQueryService queryService;

    public MonitoringPointController(MonitoringPointCommandService commandService,
                                     MonitoringPointQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a monitoring point in a project")
    @ApiResponse(responseCode = "201", description = "Monitoring point created")
    @ApiResponse(responseCode = "400", description = "Invalid monitoring point data")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @ApiResponse(responseCode = "409", description = "Project is archived")
    public ResponseEntity<MonitoringPointResource> createPoint(@PathVariable Long projectId,
            @Valid @RequestBody CreateMonitoringPointResource resource) {
        var point = commandService.createPoint(projectId, resource.name(), resource.latitude(),
                resource.longitude(), resource.normativeThresholdId());
        return ResponseEntity.created(URI.create("/api/v1/projects/" + projectId + "/monitoring-points/"
                        + point.getId()))
                .body(MonitoringPointResourceFromEntityAssembler.toResourceFromEntity(point));
    }

    @GetMapping
    @Operation(summary = "List monitoring points in a project")
    @ApiResponse(responseCode = "200", description = "Monitoring points returned")
    @ApiResponse(responseCode = "400", description = "Invalid project ID")
    @ApiResponse(responseCode = "404", description = "Project not found")
    public PageResult<MonitoringPointResource> getPointsByProject(@PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return queryService.getPointsByProjectId(projectId, page, size)
                .map(MonitoringPointResourceFromEntityAssembler::toResourceFromEntity);
    }

    @GetMapping("/{pointId}")
    @Operation(summary = "Get a monitoring point by ID")
    @ApiResponse(responseCode = "200", description = "Monitoring point found")
    @ApiResponse(responseCode = "400", description = "Invalid ID")
    @ApiResponse(responseCode = "404", description = "Project or monitoring point not found")
    public MonitoringPointResource getPointById(@PathVariable Long projectId, @PathVariable Long pointId) {
        return MonitoringPointResourceFromEntityAssembler.toResourceFromEntity(
                queryService.getPointById(projectId, pointId));
    }

    @PutMapping("/{pointId}")
    @Operation(summary = "Update a monitoring point")
    @ApiResponse(responseCode = "200", description = "Monitoring point updated")
    @ApiResponse(responseCode = "400", description = "Invalid monitoring point data")
    @ApiResponse(responseCode = "404", description = "Project or monitoring point not found")
    @ApiResponse(responseCode = "409", description = "Project is archived or monitoring point is inactive")
    public MonitoringPointResource updatePoint(@PathVariable Long projectId, @PathVariable Long pointId,
            @Valid @RequestBody UpdateMonitoringPointResource resource) {
        return MonitoringPointResourceFromEntityAssembler.toResourceFromEntity(
                commandService.updatePoint(projectId, pointId, resource.name(), resource.latitude(),
                        resource.longitude(), resource.normativeThresholdId()));
    }

    @DeleteMapping("/{pointId}")
    @Operation(summary = "Deactivate a monitoring point")
    @ApiResponse(responseCode = "204", description = "Monitoring point deactivated")
    @ApiResponse(responseCode = "400", description = "Invalid ID")
    @ApiResponse(responseCode = "404", description = "Project or monitoring point not found")
    @ApiResponse(responseCode = "409", description = "Project is archived or monitoring point is already inactive")
    public ResponseEntity<Void> deactivatePoint(@PathVariable Long projectId, @PathVariable Long pointId) {
        commandService.deactivatePoint(projectId, pointId);
        return ResponseEntity.noContent().build();
    }
}
