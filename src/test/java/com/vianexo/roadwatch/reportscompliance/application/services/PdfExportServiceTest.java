package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfExportServiceTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void exportsBasicAuditReportPdf() throws Exception {
        var report = new AuditReport(10L, "Project", LocalDate.now(), LocalDate.now(), List.of("Summary"));
        var service = new PdfExportService(temporaryDirectory.toString());

        var filePath = Path.of(service.export(report));

        assertTrue(Files.exists(filePath));
        assertTrue(Files.size(filePath) > 0);
        assertTrue(service.load(filePath.toString()).isPresent());
    }
}
