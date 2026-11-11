package com.vianexo.roadwatch.reportscompliance.domain.model.aggregates;

import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.ReportStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditReportTest {

    @Test
    void createsRequestedReport() {
        var report = new AuditReport(10L, "Project", LocalDate.now(), LocalDate.now(), List.of("Summary"));

        assertEquals(ReportStatus.REQUESTED, report.getStatus());
    }

    @Test
    void marksReportAsGenerated() {
        var report = new AuditReport(10L, "Project", LocalDate.now(), LocalDate.now(), List.of("Summary"));

        report.markGenerated("reports/report-10.pdf");

        assertEquals(ReportStatus.GENERATED, report.getStatus());
        assertEquals("reports/report-10.pdf", report.getFileUrl());
        assertNotNull(report.getGeneratedAt());
    }

    @Test
    void marksReportAsFailed() {
        var report = new AuditReport(10L, "Project", LocalDate.now(), LocalDate.now(), List.of("Summary"));

        report.markFailed();

        assertEquals(ReportStatus.FAILED, report.getStatus());
    }
}
