package com.vianexo.roadwatch.documentevidence.interfaces.rest.controllers;

import com.vianexo.roadwatch.documentevidence.application.services.EvidenceService;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.EvidenceResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evidences")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @Operation(summary = "Get all evidences")
    @GetMapping
    public ResponseEntity<List<EvidenceResource>> getEvidences() {
        var resources = evidenceService.findAll().stream()
                .map(EvidenceResourceFromEntityAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get evidence by identifier")
    @GetMapping("/{evidenceId}")
    public ResponseEntity<EvidenceResource> getEvidenceById(@PathVariable Long evidenceId) {
        return evidenceService.findById(evidenceId)
                .map(EvidenceResourceFromEntityAssembler::toResourceFrom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}