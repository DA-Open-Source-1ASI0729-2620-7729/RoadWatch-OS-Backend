package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "monitoring_point")
public class MonitoringPointJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long projectId;

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

    public MonitoringPointJpaEntity(Long projectId, String name, BigDecimal latitude, BigDecimal longitude,
                                    Long normativeThresholdId, boolean active) {
        this.projectId = projectId;
        update(name, latitude, longitude, normativeThresholdId, active);
    }

    public void update(String name, BigDecimal latitude, BigDecimal longitude, Long normativeThresholdId,
                       boolean active) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.normativeThresholdId = normativeThresholdId;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
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
