package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetNormativeThresholdsByProjectIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.NormativeThresholdCommandService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.NormativeThresholdQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.NormativeThresholdResource;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.UpsertNormativeThresholdResource;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform.NormativeThresholdResourceFromEntityAssembler;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform.UpsertNormativeThresholdCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/normative-thresholds", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Normative Thresholds", description = "Normative Threshold Configuration Endpoints")
public class NormativeThresholdsController {

    private final NormativeThresholdCommandService commandService;
    private final NormativeThresholdQueryService queryService;

    public NormativeThresholdsController(
            NormativeThresholdCommandService commandService,
            NormativeThresholdQueryService queryService
    ) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create or update a normative threshold for a project indicator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Normative threshold saved"),
            @ApiResponse(responseCode = "400", description = "Invalid threshold configuration")
    })
    public ResponseEntity<NormativeThresholdResource> upsertThreshold(
            @RequestBody UpsertNormativeThresholdResource resource
    ) {
        var command = UpsertNormativeThresholdCommandFromResourceAssembler.toCommandFromResource(resource);
        var threshold = commandService.handle(command);
        if (threshold.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var thresholdResource = NormativeThresholdResourceFromEntityAssembler.toResourceFromEntity(threshold.get());
        return new ResponseEntity<>(thresholdResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get active normative thresholds by project ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Normative thresholds retrieved")
    })
    public ResponseEntity<List<NormativeThresholdResource>> getThresholdsByProject(@RequestParam Long projectId) {
        var thresholds = queryService.handle(new GetNormativeThresholdsByProjectIdQuery(projectId));
        var resources = thresholds.stream()
                .map(NormativeThresholdResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}