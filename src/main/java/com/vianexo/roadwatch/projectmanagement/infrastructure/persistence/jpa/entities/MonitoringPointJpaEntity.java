package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "monitoring_points")
public class MonitoringPointJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_monitoring_points_project"))
    private ProjectJpaEntity project;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    private Long normativeThresholdId;

    @Column(nullable = false)
    private boolean active;

    protected MonitoringPointJpaEntity() {
    }

    public MonitoringPointJpaEntity(ProjectJpaEntity project, String name, BigDecimal latitude, BigDecimal longitude,
                                    Long normativeThresholdId, boolean active) {
        this.project = project;
        this.name = name;
        update(latitude, longitude, normativeThresholdId, active);
    }

    public void update(BigDecimal latitude, BigDecimal longitude, Long normativeThresholdId, boolean active) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.normativeThresholdId = normativeThresholdId;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return project.getId();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Long getNormativeThresholdId() {
        return normativeThresholdId;
    }

    public boolean isActive() {
        return active;
    }
}
