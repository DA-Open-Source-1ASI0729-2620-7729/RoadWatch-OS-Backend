package com.vianexo.roadwatch.environmentalmonitoring.application.acl;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementsByMonitoringPointIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.acl.EnvironmentalMonitoringContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalMonitoringContextFacadeImpl implements EnvironmentalMonitoringContextFacade {

    private final EnvironmentalMeasurementQueryService measurementQueryService;

    public EnvironmentalMonitoringContextFacadeImpl(EnvironmentalMeasurementQueryService measurementQueryService) {
        this.measurementQueryService = measurementQueryService;
    }

    @Override
    public List<Long> findMeasurementIdsByMonitoringPointIds(List<Long> monitoringPointIds) {
        return measurementsFor(monitoringPointIds).stream()
                .map(EnvironmentalMeasurement::getId)
                .toList();
    }

    @Override
    public int countCompliantMeasurements(List<Long> monitoringPointIds) {
        return countMeasurementsByStatus(monitoringPointIds, MeasurementStatus.COMPLIANT);
    }

    @Override
    public int countNonCompliantMeasurements(List<Long> monitoringPointIds) {
        return countMeasurementsByStatus(monitoringPointIds, MeasurementStatus.NON_COMPLIANT);
    }

    private int countMeasurementsByStatus(List<Long> monitoringPointIds, MeasurementStatus status) {
        return (int) measurementsFor(monitoringPointIds).stream()
                .filter(measurement -> measurement.getStatus() == status)
                .count();
    }

    private List<EnvironmentalMeasurement> measurementsFor(List<Long> monitoringPointIds) {
        return monitoringPointIds.stream()
                .flatMap(pointId -> measurementQueryService.handle(
                        new GetEnvironmentalMeasurementsByMonitoringPointIdQuery(pointId)).stream())
                .toList();
    }
}
