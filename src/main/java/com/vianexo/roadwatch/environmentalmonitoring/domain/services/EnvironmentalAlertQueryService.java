package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetAllEnvironmentalAlertsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetRecentEnvironmentalAlertsQuery;

import java.util.List;

public interface EnvironmentalAlertQueryService {
    List<EnvironmentalAlert> handle(GetAllEnvironmentalAlertsQuery query);
    List<EnvironmentalAlert> handle(GetRecentEnvironmentalAlertsQuery query);
}
