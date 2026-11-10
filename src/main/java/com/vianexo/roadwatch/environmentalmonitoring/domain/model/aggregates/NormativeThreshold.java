package com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.UpsertNormativeThresholdCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
public class NormativeThreshold extends AuditableAbstractPersistenceEntity {

    @Embedded
    private ProjectId projectId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private IndicatorType indicatorType;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal warningLimit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maxPermissibleLimit;

    @Column(nullable = false, length = 120)
    private String normativeReference;

    @Column(nullable = false)
    private boolean active;

    public NormativeThreshold(UpsertNormativeThresholdCommand command) {
        validateLimits(command.warningLimit(), command.maxPermissibleLimit());
        this.projectId = new ProjectId(command.projectId());
        this.indicatorType = command.indicatorType();
        this.unit = command.unit();
        this.warningLimit = command.warningLimit();
        this.maxPermissibleLimit = command.maxPermissibleLimit();
        this.normativeReference = command.normativeReference();
        this.active = true;
    }

    public void updateLimits(UpsertNormativeThresholdCommand command) {
        validateLimits(command.warningLimit(), command.maxPermissibleLimit());
        this.unit = command.unit();
        this.warningLimit = command.warningLimit();
        this.maxPermissibleLimit = command.maxPermissibleLimit();
        this.normativeReference = command.normativeReference();
        this.active = true;
    }

    public MeasurementStatus evaluate(BigDecimal value) {
        if (value.compareTo(this.maxPermissibleLimit) >= 0) {
            return MeasurementStatus.NON_COMPLIANT;
        }
        if (value.compareTo(this.warningLimit) >= 0) {
            return MeasurementStatus.WARNING;
        }
        return MeasurementStatus.COMPLIANT;
    }

    private void validateLimits(BigDecimal warning, BigDecimal max) {
        if (warning == null || max == null || warning.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Threshold limits must be non-negative");
        }
        if (warning.compareTo(max) > 0) {
            throw new IllegalArgumentException("Warning limit cannot exceed max permissible limit");
        }
    }
}
