package com.vianexo.roadwatch.reportscompliance.domain.model.entities;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class ComplianceSummary extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private Integer compliantMeasurements;

    @Column(nullable = false)
    private Integer nonCompliantMeasurements;

    @Column(nullable = false)
    private Double compliancePercentage;

    @Column(nullable = false)
    private LocalDateTime calculatedAt;

    public ComplianceSummary(Long projectId) {
        this.projectId = projectId;
        recalculate(0, 0);
    }

    public void recalculate(Integer compliantMeasurements, Integer nonCompliantMeasurements) {
        this.compliantMeasurements = compliantMeasurements;
        this.nonCompliantMeasurements = nonCompliantMeasurements;
        var totalMeasurements = compliantMeasurements + nonCompliantMeasurements;
        this.compliancePercentage = totalMeasurements == 0
                ? 0.0
                : compliantMeasurements * 100.0 / totalMeasurements;
        this.calculatedAt = LocalDateTime.now();
    }
}
