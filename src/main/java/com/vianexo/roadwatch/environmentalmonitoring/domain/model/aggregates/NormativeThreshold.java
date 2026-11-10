package com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.UpsertNormativeThresholdCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
public class NormativeThreshold extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private IndicatorType indicatorType;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal warningLimit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maximumLimit;

    public NormativeThreshold(UpsertNormativeThresholdCommand command) {
        validateLimits(command.warningLimit(), command.maximumLimit());
        this.indicatorType = command.indicatorType();
        this.unit = command.unit();
        this.warningLimit = command.warningLimit();
        this.maximumLimit = command.maximumLimit();
    }

    public void updateLimits(UpsertNormativeThresholdCommand command) {
        updateLimits(command.warningLimit(), command.maximumLimit());
    }

    public void updateLimits(BigDecimal warningLimit, BigDecimal maximumLimit) {
        validateLimits(warningLimit, maximumLimit);
        this.warningLimit = warningLimit;
        this.maximumLimit = maximumLimit;
    }

    public boolean isWarning(BigDecimal value) {
        return value.compareTo(this.warningLimit) >= 0;
    }

    public boolean isExceeded(BigDecimal value) {
        return value.compareTo(this.maximumLimit) >= 0;
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
