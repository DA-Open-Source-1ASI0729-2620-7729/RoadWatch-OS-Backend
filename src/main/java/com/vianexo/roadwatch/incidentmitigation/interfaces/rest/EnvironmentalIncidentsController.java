package com.vianexo.roadwatch.incidentmitigation.interfaces.rest;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentSeverityCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentStatusCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.MarkOverdueIncidentsCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetAllEnvironmentalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetCriticalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetEnvironmentalIncidentByIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentStatusHistoryQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentQueryService;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.ChangeIncidentSeverityResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.ChangeIncidentStatusResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.CreateEnvironmentalIncidentResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.EnvironmentalIncidentResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.IncidentStatusHistoryResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.MarkOverdueIncidentsResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform.CreateEnvironmentalIncidentCommandFromResourceAssembler;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform.EnvironmentalIncidentResourceFromEntityAssembler;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform.IncidentStatusHistoryResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/incidents")
@Tag(name = "Incident & Mitigation", description = "Environmental incident management")
public class EnvironmentalIncidentsController {

    private final EnvironmentalIncidentCommandService commandService;
    private final EnvironmentalIncidentQueryService queryService;

    public EnvironmentalIncidentsController(EnvironmentalIncidentCommandService commandService,
                                            EnvironmentalIncidentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create an environmental incident")
    @ApiResponse(responseCode = "201", description = "Incident created")
    @ApiResponse(responseCode = "400", description = "Invalid incident data")
    public ResponseEntity<EnvironmentalIncidentResource> createIncident(
            @Valid @RequestBody CreateEnvironmentalIncidentResource resource) {
        var incident = commandService.handle(
                CreateEnvironmentalIncidentCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntity.created(URI.create("/api/v1/incidents/" + incident.getId()))
                .body(EnvironmentalIncidentResourceFromEntityAssembler.toResourceFromEntity(incident));
    }

    @GetMapping
    @Operation(summary = "List incidents, optionally filtered by status and severity")
    @ApiResponse(responseCode = "200", description = "Incidents returned")
    @ApiResponse(responseCode = "400", description = "Invalid status or severity")
    public List<EnvironmentalIncidentResource> getAllIncidents(
            @Parameter(in = ParameterIn.QUERY, description = "OPEN, IN_PROGRESS, RESOLVED, CLOSED or OVERDUE")
            @RequestParam(required = false) IncidentStatus status,
            @Parameter(in = ParameterIn.QUERY, description = "LOW, MEDIUM, HIGH or CRITICAL")
            @RequestParam(required = false) IncidentSeverity severity) {
        return queryService.handle(new GetAllEnvironmentalIncidentsQuery(status, severity)).stream()
                .map(EnvironmentalIncidentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/critical")
    @Operation(summary = "List critical incidents that are not closed")
    @ApiResponse(responseCode = "200", description = "Critical incidents returned")
    public List<EnvironmentalIncidentResource> getCriticalIncidents() {
        return queryService.handle(new GetCriticalIncidentsQuery()).stream()
                .map(EnvironmentalIncidentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/{incidentId}")
    @Operation(summary = "Get an incident by ID")
    @ApiResponse(responseCode = "200", description = "Incident found")
    @ApiResponse(responseCode = "400", description = "Invalid incident ID")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    public EnvironmentalIncidentResource getIncidentById(@PathVariable Long incidentId) {
        return queryService.handle(new GetEnvironmentalIncidentByIdQuery(incidentId))
                .map(EnvironmentalIncidentResourceFromEntityAssembler::toResourceFromEntity)
                .orElseThrow(() -> new NoSuchElementException("Incident not found: " + incidentId));
    }

    @PatchMapping("/{incidentId}/status")
    @Operation(summary = "Change the status of an incident")
    @ApiResponse(responseCode = "200", description = "Status changed and recorded in the history")
    @ApiResponse(responseCode = "400", description = "Invalid status data")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    @ApiResponse(responseCode = "409", description = "Status transition not allowed")
    public EnvironmentalIncidentResource changeStatus(@PathVariable Long incidentId,
                                                      @Valid @RequestBody ChangeIncidentStatusResource resource) {
        return EnvironmentalIncidentResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(
                new ChangeIncidentStatusCommand(incidentId, resource.status(), resource.changedByUserId())));
    }

    @PatchMapping("/{incidentId}/severity")
    @Operation(summary = "Change the severity of an incident")
    @ApiResponse(responseCode = "200", description = "Severity changed")
    @ApiResponse(responseCode = "400", description = "Invalid severity")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    @ApiResponse(responseCode = "409", description = "Incident is closed")
    public EnvironmentalIncidentResource changeSeverity(@PathVariable Long incidentId,
                                                        @Valid @RequestBody ChangeIncidentSeverityResource resource) {
        return EnvironmentalIncidentResourceFromEntityAssembler.toResourceFromEntity(
                commandService.handle(new ChangeIncidentSeverityCommand(incidentId, resource.severity())));
    }

    @PostMapping("/overdue-checks")
    @Operation(summary = "Mark as overdue the open incidents older than the given number of days")
    @ApiResponse(responseCode = "200", description = "Incidents marked as overdue")
    @ApiResponse(responseCode = "400", description = "Invalid check data")
    public List<EnvironmentalIncidentResource> markOverdueIncidents(
            @Valid @RequestBody MarkOverdueIncidentsResource resource) {
        return commandService.handle(new MarkOverdueIncidentsCommand(resource.maxOpenDays(),
                        resource.changedByUserId())).stream()
                .map(EnvironmentalIncidentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/{incidentId}/status-history")
    @Operation(summary = "Get the status history of an incident")
    @ApiResponse(responseCode = "200", description = "Status history returned")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    public List<IncidentStatusHistoryResource> getStatusHistory(@PathVariable Long incidentId) {
        return queryService.handle(new GetIncidentStatusHistoryQuery(incidentId)).stream()
                .map(IncidentStatusHistoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}
