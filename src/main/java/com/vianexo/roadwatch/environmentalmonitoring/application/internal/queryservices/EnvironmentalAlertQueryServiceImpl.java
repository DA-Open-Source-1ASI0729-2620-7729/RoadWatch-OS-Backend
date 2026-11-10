package com.vianexo.roadwatch.environmentalmonitoring.application.internal.queryservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetAllEnvironmentalAlertsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetRecentEnvironmentalAlertsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalAlertQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalAlertQueryServiceImpl implements EnvironmentalAlertQueryService {

    private final EnvironmentalAlertRepository alertRepository;

    public EnvironmentalAlertQueryServiceImpl(EnvironmentalAlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public List<EnvironmentalAlert> handle(GetAllEnvironmentalAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public List<EnvironmentalAlert> handle(GetRecentEnvironmentalAlertsQuery query) {
        return alertRepository.findTop10ByOrderByCreatedAtDesc();
    }
}
