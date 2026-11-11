package com.vianexo.roadwatch.reportscompliance.application.services;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfExportService {

    private final Path reportsDirectory;

    public PdfExportService(@Value("${app.storage.upload-dir:uploads}") String uploadDirectory) {
        this.reportsDirectory = Paths.get(uploadDirectory).toAbsolutePath().normalize().resolve("reports");
    }

    public String export(AuditReport report) {
        var fileName = "audit-report-" + report.getProjectId() + ".pdf";
        var reportPath = reportsDirectory.resolve(fileName).normalize();
        if (!reportPath.startsWith(reportsDirectory)) {
            throw new IllegalArgumentException("Invalid report file path");
        }

        try {
            Files.createDirectories(reportsDirectory);
            // basado en la documentacion oficial de openpdf
            // https://github.com/LibrePDF/OpenPDF
            var document = new Document();
            PdfWriter.getInstance(document, Files.newOutputStream(reportPath));
            // crea el documento pdf
            document.open();
            document.add(new Paragraph("Audit Report"));
            document.add(new Paragraph("Project: " + report.getProjectName()));
            document.add(new Paragraph("Period: " + report.getPeriodStart() + " - " + report.getPeriodEnd()));
            document.add(new Paragraph("Status: " + report.getStatus()));
            document.close();
            return reportPath.toString();
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not export report", exception);
        } catch (DocumentException exception) {
            throw new IllegalStateException("Could not create report PDF", exception);
        }
    }
}
