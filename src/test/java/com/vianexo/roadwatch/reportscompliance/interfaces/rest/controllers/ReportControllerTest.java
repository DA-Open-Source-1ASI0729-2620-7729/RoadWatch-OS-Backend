package com.vianexo.roadwatch.reportscompliance.interfaces.rest.controllers;

import com.vianexo.roadwatch.reportscompliance.application.services.PdfExportService;
import com.vianexo.roadwatch.reportscompliance.application.services.ReportService;
import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportControllerTest {

    @Test
    void downloadsReportPdf() {
        var reportService = mock(ReportService.class);
        var pdfExportService = mock(PdfExportService.class);
        var controller = new ReportController(reportService, pdfExportService);
        var report = new AuditReport(10L, "Project", LocalDate.now(), LocalDate.now(), List.of("Summary"));
        var filePath = "reports/audit-report-1.pdf";

        when(reportService.findById(1L)).thenReturn(Optional.of(report));
        when(pdfExportService.export(report)).thenReturn(filePath);
        when(reportService.markGenerated(1L, filePath)).thenReturn(Optional.of(report));
        when(pdfExportService.load(filePath)).thenReturn(Optional.of(new ByteArrayResource(new byte[]{1})));

        var response = controller.downloadReportPdf(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());
        assertNotNull(response.getBody());
    }
}
