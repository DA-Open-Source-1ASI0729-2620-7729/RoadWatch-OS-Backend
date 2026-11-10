package com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class EnvironmentalMeasurement extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long monitoringPointId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private IndicatorType indicatorType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MeasurementStatus status;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    public EnvironmentalMeasurement(
            RegisterEnvironmentalMeasurementCommand command,
            MeasurementStatus evaluatedStatus
    ) {
        this.monitoringPointId = command.monitoringPointId();
        this.indicatorType = command.indicatorType();
        this.value = command.value();
        this.status = evaluatedStatus;
        this.measuredAt = command.measuredAt() != null ? command.measuredAt() : LocalDateTime.now();
    }

    public void markCompliant() {
        this.status = MeasurementStatus.COMPLIANT;
    }

    public void markWarning() {
        this.status = MeasurementStatus.WARNING;
    }

    public void markNonCompliant() {
        this.status = MeasurementStatus.NON_COMPLIANT;
    }
}
