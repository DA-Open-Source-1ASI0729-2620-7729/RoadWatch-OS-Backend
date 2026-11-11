package com.vianexo.roadwatch.projectmanagement.domain.model.entities;

public class MonitoringPoint {

    private final Long id;
    private final Long projectId;
    private final String name;
    private double latitude;
    private double longitude;
    private Long normativeThresholdId;
    private boolean active;

    public MonitoringPoint(Long projectId, String name, double latitude, double longitude,
                           Long normativeThresholdId) {
        this(null, projectId, name, latitude, longitude, normativeThresholdId, true);
    }

    public MonitoringPoint(Long id, Long projectId, String name, double latitude, double longitude,
                           Long normativeThresholdId, boolean active) {
        validatePositiveId(id, "Monitoring point id", false);
        validatePositiveId(projectId, "Project id", true);
        validatePositiveId(normativeThresholdId, "Normative threshold id", false);
        validateCoordinates(latitude, longitude);
        this.id = id;
        this.projectId = projectId;
        this.name = requiredName(name);
        this.latitude = latitude;
        this.longitude = longitude;
        this.normativeThresholdId = normativeThresholdId;
        this.active = active;
    }

    public void updateLocation(double latitude, double longitude) {
        requireActive();
        validateCoordinates(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateThreshold(Long normativeThresholdId) {
        requireActive();
        validatePositiveId(normativeThresholdId, "Normative threshold id", false);
        this.normativeThresholdId = normativeThresholdId;
    }

    public void deactivate() {
        requireActive();
        this.active = false;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Long getNormativeThresholdId() {
        return normativeThresholdId;
    }

    public boolean isActive() {
        return active;
    }

    private void requireActive() {
        if (!active) {
            throw new IllegalStateException("Inactive monitoring points cannot be changed");
        }
    }

    private static String requiredName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Monitoring point name is required");
        }
        return name.trim();
    }

    private static void validatePositiveId(Long id, String field, boolean required) {
        if (id == null && required) {
            throw new IllegalArgumentException(field + " is required");
        }
        if (id != null && id <= 0) {
            throw new IllegalArgumentException(field + " must be positive");
        }
    }

    private static void validateCoordinates(double latitude, double longitude) {
        if (!Double.isFinite(latitude) || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (!Double.isFinite(longitude) || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }
}
