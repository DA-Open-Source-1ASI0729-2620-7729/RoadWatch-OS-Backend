package com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MonitoringPointId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
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

    @Embedded
    private ProjectId projectId;

    @Embedded
    private MonitoringPointId monitoringPointId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private IndicatorType indicatorType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal measuredValue;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal thresholdApplied;

    @Column(nullable = false, length = 20)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MeasurementStatus status;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    @Column(nullable = false)
    private Long registeredByUserId;

    @Column(length = 500)
    private String observation;

    public EnvironmentalMeasurement(
            RegisterEnvironmentalMeasurementCommand command,
            BigDecimal thresholdApplied,
            MeasurementStatus evaluatedStatus
    ) {
        this.projectId = new ProjectId(command.projectId());
        this.monitoringPointId = new MonitoringPointId(command.monitoringPointId());
        this.indicatorType = command.indicatorType();
        this.measuredValue = command.measuredValue();
        this.thresholdApplied = thresholdApplied;
        this.unit = command.unit();
        this.status = evaluatedStatus;
        this.measuredAt = command.measuredAt() != null ? command.measuredAt() : LocalDateTime.now();
        this.registeredByUserId = command.registeredByUserId();
        this.observation = command.observation();
    }
}