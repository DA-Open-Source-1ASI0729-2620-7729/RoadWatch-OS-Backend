package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.AuditReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final AuditReportRepository reportRepository;

    public ReportService(AuditReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<AuditReport> findAll() {
        return reportRepository.findAll();
    }

    public Optional<AuditReport> findById(Long id) {
        return reportRepository.findById(id);
    }

    public AuditReport generate(Long projectId, LocalDate periodStart, LocalDate periodEnd,
                                List<String> sections) {
        var projectName = "Proyecto " + projectId;
        return reportRepository.save(
                new AuditReport(projectId, projectName, periodStart, periodEnd, sections));
    }

    public Optional<AuditReport> markGenerated(Long id, String fileUrl) {
        return reportRepository.findById(id).map(report -> {
            report.markGenerated(fileUrl);
            return reportRepository.save(report);
        });
    }
}
