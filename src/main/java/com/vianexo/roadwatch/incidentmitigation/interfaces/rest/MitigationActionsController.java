package com.vianexo.roadwatch.incidentmitigation.interfaces.rest;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CompleteMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.RegisterMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetMitigationActionsByIncidentIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.services.MitigationActionCommandService;
import com.vianexo.roadwatch.incidentmitigation.domain.services.MitigationActionQueryService;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.MitigationActionResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.RegisterMitigationActionResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform.MitigationActionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents/{incidentId}/mitigation-actions")
@Tag(name = "Incident & Mitigation", description = "Environmental incident management")
public class MitigationActionsController {

    private final MitigationActionCommandService commandService;
    private final MitigationActionQueryService queryService;

    public MitigationActionsController(MitigationActionCommandService commandService,
                                       MitigationActionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Register a mitigation action for an incident")
    @ApiResponse(responseCode = "201", description = "Mitigation action registered")
    @ApiResponse(responseCode = "400", description = "Invalid mitigation action data")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    @ApiResponse(responseCode = "409", description = "Incident is closed")
    public ResponseEntity<MitigationActionResource> registerMitigationAction(
            @PathVariable Long incidentId, @Valid @RequestBody RegisterMitigationActionResource resource) {
        var action = commandService.handle(new RegisterMitigationActionCommand(incidentId, resource.description(),
                resource.responsibleUserId()));
        return ResponseEntity.created(URI.create(
                        "/api/v1/incidents/%d/mitigation-actions/%d".formatted(incidentId, action.getId())))
                .body(MitigationActionResourceFromEntityAssembler.toResourceFromEntity(action));
    }

    @GetMapping
    @Operation(summary = "List the mitigation actions of an incident")
    @ApiResponse(responseCode = "200", description = "Mitigation actions returned")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    public List<MitigationActionResource> getMitigationActions(@PathVariable Long incidentId) {
        return queryService.handle(new GetMitigationActionsByIncidentIdQuery(incidentId)).stream()
                .map(MitigationActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PatchMapping("/{mitigationActionId}/completion")
    @Operation(summary = "Complete a mitigation action")
    @ApiResponse(responseCode = "200", description = "Mitigation action completed")
    @ApiResponse(responseCode = "404", description = "Incident or mitigation action not found")
    @ApiResponse(responseCode = "409", description = "Action already completed or incident closed")
    public MitigationActionResource completeMitigationAction(@PathVariable Long incidentId,
                                                             @PathVariable Long mitigationActionId) {
        return MitigationActionResourceFromEntityAssembler.toResourceFromEntity(
                commandService.handle(new CompleteMitigationActionCommand(incidentId, mitigationActionId)));
    }
}
