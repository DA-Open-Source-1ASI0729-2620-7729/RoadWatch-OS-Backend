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
public class EnvironmentalKpi extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private Double compliancePercentage;

    private Double averageResolutionTime;

    @Column(nullable = false)
    private Integer openIncidents;

    @Column(nullable = false)
    private Integer criticalIncidents;

    @Column(nullable = false)
    private LocalDateTime calculatedAt;

    public EnvironmentalKpi(Long projectId) {
        this.projectId = projectId;
        recalculate(0.0, null, 0, 0);
    }

    public void recalculate(Double compliancePercentage, Double averageResolutionTime,
                            Integer openIncidents, Integer criticalIncidents) {
        this.compliancePercentage = compliancePercentage;
        this.averageResolutionTime = averageResolutionTime;
        this.openIncidents = openIncidents;
        this.criticalIncidents = criticalIncidents;
        this.calculatedAt = LocalDateTime.now();
    }
}
