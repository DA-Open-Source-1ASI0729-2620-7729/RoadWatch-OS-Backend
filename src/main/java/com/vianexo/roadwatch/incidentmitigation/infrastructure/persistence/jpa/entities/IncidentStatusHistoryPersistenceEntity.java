package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_status_history")
@Getter
@Setter
@NoArgsConstructor
public class IncidentStatusHistoryPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private EnvironmentalIncidentPersistenceEntity incident;

    @Enumerated(EnumType.STRING)
    private IncidentStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus newStatus;

    @Column(nullable = false)
    private Long changedByUserId;

    @Column(nullable = false)
    private LocalDateTime changedAt;
}
