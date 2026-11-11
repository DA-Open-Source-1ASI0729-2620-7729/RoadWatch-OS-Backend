package com.vianexo.roadwatch.environmentalmonitoring.interfaces.acl;

import java.util.List;

public interface EnvironmentalMonitoringContextFacade {

    List<Long> findMeasurementIdsByMonitoringPointIds(List<Long> monitoringPointIds);

    int countCompliantMeasurements(List<Long> monitoringPointIds);

    int countNonCompliantMeasurements(List<Long> monitoringPointIds);
}
