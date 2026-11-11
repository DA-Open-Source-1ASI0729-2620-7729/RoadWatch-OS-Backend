package com.vianexo.roadwatch.reportscompliance.interfaces.rest.controllers;

import com.vianexo.roadwatch.reportscompliance.application.services.PdfExportService;
import com.vianexo.roadwatch.reportscompliance.application.services.ReportService;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.AuditReportResource;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.CreateAuditReportResource;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform.AuditReportResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class ReportController {

    private final ReportService reportService;
    private final PdfExportService pdfExportService;

    public ReportController(ReportService reportService, PdfExportService pdfExportService) {
        this.reportService = reportService;
        this.pdfExportService = pdfExportService;
    }

    @Operation(summary = "Get all reports")
    @GetMapping
    public ResponseEntity<List<AuditReportResource>> getReports() {
        var resources = reportService.findAll().stream()
                .map(AuditReportResourceFromEntityAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get report by identifier")
    @GetMapping("/{reportId}")
    public ResponseEntity<AuditReportResource> getReportById(@PathVariable Long reportId) {
        return reportService.findById(reportId)
                .map(AuditReportResourceFromEntityAssembler::toResourceFrom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Generate a report")
    @PostMapping
    public ResponseEntity<AuditReportResource> generateReport(
            @Valid @RequestBody CreateAuditReportResource resource) {
        if (resource.periodEnd().isBefore(resource.periodStart())) {
            return ResponseEntity.badRequest().build();
        }
        var report = reportService.generate(resource.projectId(), resource.periodStart(),
                resource.periodEnd(), resource.sections());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuditReportResourceFromEntityAssembler.toResourceFrom(report));
    }

    @Operation(summary = "Issue a report")
    @PostMapping("/{reportId}/issue")
    public ResponseEntity<AuditReportResource> issueReport(@PathVariable Long reportId) {
        return reportService.markGenerated(reportId, null)
                .map(AuditReportResourceFromEntityAssembler::toResourceFrom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Download report PDF")
    @GetMapping("/{reportId}/pdf")
    public ResponseEntity<Resource> downloadReportPdf(@PathVariable Long reportId) {
        var report = reportService.findById(reportId);
        if (report.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var filePath = pdfExportService.export(report.get());
        reportService.markGenerated(reportId, filePath);
        var file = pdfExportService.load(filePath);
        if (file.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var contentDisposition = ContentDisposition.attachment()
                .filename("audit-report-" + reportId + ".pdf", StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(file.get());
    }

}
