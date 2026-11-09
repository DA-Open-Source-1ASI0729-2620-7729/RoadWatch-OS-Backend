package com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.*;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class EnvironmentalAlert extends AuditableAbstractPersistenceEntity<EnvironmentalAlert> {

    @Embedded
    private ProjectId projectId;

    @Embedded
    private MonitoringPointId monitoringPointId;

    @Column(nullable = false)
    private Long measurementId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private IndicatorType indicatorType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal recordedValue;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal thresholdLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AlertSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AlertStatus status;

    @Column(nullable = false, length = 300)
    private String message;

    @Column(nullable = false)
    private LocalDateTime triggeredAt;

    public EnvironmentalAlert(
            Long projectId,
            Long monitoringPointId,
            Long measurementId,
            IndicatorType indicatorType,
            BigDecimal recordedValue,
            BigDecimal thresholdLimit,
            AlertSeverity severity,
            String message
    ) {
        this.projectId = new ProjectId(projectId);
        this.monitoringPointId = new MonitoringPointId(monitoringPointId);
        this.measurementId = measurementId;
        this.indicatorType = indicatorType;
        this.recordedValue = recordedValue;
        this.thresholdLimit = thresholdLimit;
        this.severity = severity;
        this.status = AlertStatus.OPEN;
        this.message = message;
        this.triggeredAt = LocalDateTime.now();
    }

    public void acknowledge() {
        if (this.status == AlertStatus.RESOLVED) {
            throw new IllegalStateException("Cannot acknowledge an already resolved alert");
        }
        this.status = AlertStatus.ACKNOWLEDGED;
    }

    public void resolve() {
        this.status = AlertStatus.RESOLVED;
    }
}