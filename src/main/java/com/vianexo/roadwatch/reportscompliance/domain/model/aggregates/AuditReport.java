package com.vianexo.roadwatch.reportscompliance.domain.model.aggregates;

import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.AuditReportStatus;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class AuditReport extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private LocalDate periodStart;

    @Column(nullable = false)
    private LocalDate periodEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditReportStatus status;

    private LocalDate issuedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> sections = new ArrayList<>();

    public AuditReport(Long projectId, String projectName,
                       LocalDate periodStart, LocalDate periodEnd,
                       List<String> sections) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.sections = new ArrayList<>(sections);
        this.status = AuditReportStatus.DRAFT;
    }

    public void issue() {
        if (status == AuditReportStatus.ISSUED) {
            throw new IllegalStateException("El reporte ya fue emitido");
        }
        this.status = AuditReportStatus.ISSUED;
        this.issuedAt = LocalDate.now();
    }

    public void observe() {
        if (status != AuditReportStatus.ISSUED) {
            throw new IllegalStateException("Solo se puede observar un reporte emitido");
        }
        this.status = AuditReportStatus.OBSERVED;
    }
}
