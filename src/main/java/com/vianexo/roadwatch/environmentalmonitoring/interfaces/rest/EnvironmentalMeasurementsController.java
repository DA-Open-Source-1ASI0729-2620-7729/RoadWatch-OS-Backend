package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetAllEnvironmentalMeasurementsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementByIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementsByMonitoringPointIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementsByDateRangeQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementCommandService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.EnvironmentalMeasurementResource;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.RegisterEnvironmentalMeasurementResource;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform.EnvironmentalMeasurementResourceFromEntityAssembler;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform.RegisterEnvironmentalMeasurementCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/environmental-measurements", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Environmental Measurements", description = "Environmental Measurement Management Endpoints")
public class EnvironmentalMeasurementsController {

    private final EnvironmentalMeasurementCommandService commandService;
    private final EnvironmentalMeasurementQueryService queryService;

    public EnvironmentalMeasurementsController(
            EnvironmentalMeasurementCommandService commandService,
            EnvironmentalMeasurementQueryService queryService
    ) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new environmental measurement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Environmental measurement registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<EnvironmentalMeasurementResource> registerMeasurement(
            @RequestBody RegisterEnvironmentalMeasurementResource resource
    ) {
        var command = RegisterEnvironmentalMeasurementCommandFromResourceAssembler.toCommandFromResource(resource);
        var measurement = commandService.handle(command);
        var measurementResource = EnvironmentalMeasurementResourceFromEntityAssembler.toResourceFromEntity(measurement);
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementResource);
    }

    @GetMapping
    @Operation(summary = "Get environmental measurements with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurements retrieved")
    })
    public ResponseEntity<List<EnvironmentalMeasurementResource>> getMeasurements(
            @RequestParam(required = false) Long monitoringPointId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if ((startDate == null) != (endDate == null)
                || (startDate != null && monitoringPointId == null)) {
            return ResponseEntity.badRequest().build();
        }

        var measurements = queryService.handle(new GetAllEnvironmentalMeasurementsQuery());

        if (monitoringPointId != null && startDate != null && endDate != null) {
            measurements = queryService.handle(new GetEnvironmentalMeasurementsByDateRangeQuery(
                    monitoringPointId, startDate, endDate));
        } else if (monitoringPointId != null) {
            measurements = queryService.handle(
                    new GetEnvironmentalMeasurementsByMonitoringPointIdQuery(monitoringPointId));
        }

        var resources = measurements.stream()
                .map(EnvironmentalMeasurementResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{measurementId}")
    @Operation(summary = "Get environmental measurement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurement found"),
            @ApiResponse(responseCode = "404", description = "Measurement not found")
    })
    public ResponseEntity<EnvironmentalMeasurementResource> getMeasurementById(@PathVariable Long measurementId) {
        var measurement = queryService.handle(new GetEnvironmentalMeasurementByIdQuery(measurementId));
        if (measurement.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = EnvironmentalMeasurementResourceFromEntityAssembler.toResourceFromEntity(measurement.get());
        return ResponseEntity.ok(resource);
    }
}
