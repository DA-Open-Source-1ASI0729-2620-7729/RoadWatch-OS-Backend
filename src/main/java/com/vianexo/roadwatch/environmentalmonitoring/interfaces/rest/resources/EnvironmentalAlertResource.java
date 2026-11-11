package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources;

import java.util.Date;

public record EnvironmentalAlertResource(
        Long id,
        Long measurementId,
        String message,
        Date createdAt,
        boolean acknowledged
) {}
