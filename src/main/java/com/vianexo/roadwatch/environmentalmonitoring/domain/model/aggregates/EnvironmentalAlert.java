package com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class EnvironmentalAlert extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long measurementId;

    @Column(nullable = false)
    private boolean acknowledged;

    @Column(nullable = false, length = 300)
    private String message;

    public EnvironmentalAlert(
            Long measurementId,
            String message
    ) {
        this.measurementId = measurementId;
        this.acknowledged = false;
        this.message = message;
    }

    public void acknowledge() {
        this.acknowledged = true;
    }
}
