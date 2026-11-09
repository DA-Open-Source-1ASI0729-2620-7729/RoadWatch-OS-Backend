package com.vianexo.roadwatch.documentevidence.interfaces.rest.controllers;

import com.vianexo.roadwatch.documentevidence.application.services.EvidenceService;
import com.vianexo.roadwatch.documentevidence.application.services.FileStorageService;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.EvidenceResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/evidences")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class EvidenceController {

    private final EvidenceService evidenceService;
    private final FileStorageService fileStorageService;

    public EvidenceController(EvidenceService evidenceService, FileStorageService fileStorageService) {
        this.evidenceService = evidenceService;
        this.fileStorageService = fileStorageService;
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

    @Operation(summary = "Upload an evidence file")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvidenceResource> uploadEvidence(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploadedByUserId") Long uploadedByUserId,
            @RequestParam(value = "incidentId", required = false) Long incidentId,
            @RequestParam(value = "measurementId", required = false) Long measurementId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var fileName = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
        var fileType = Objects.requireNonNullElse(file.getContentType(), "application/octet-stream");
        var storedFileName = fileStorageService.store(file);
        var evidence = evidenceService.create(
                incidentId, measurementId, fileName, fileType, storedFileName, uploadedByUserId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EvidenceResourceFromEntityAssembler.toResourceFrom(evidence));
    }

    @Operation(summary = "Download an evidence file")
    @GetMapping("/{evidenceId}/file")
    public ResponseEntity<Resource> downloadEvidenceFile(@PathVariable Long evidenceId) {
        var evidence = evidenceService.findById(evidenceId);
        if (evidence.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var file = fileStorageService.load(evidence.get().getStoredFileName());
        if (file.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var contentDisposition = ContentDisposition.attachment()
                .filename(evidence.get().getFileName(), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.parseMediaType(evidence.get().getFileType()))
                .body(file.get());
    }
}