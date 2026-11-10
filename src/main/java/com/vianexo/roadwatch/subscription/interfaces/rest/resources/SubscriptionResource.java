package com.vianexo.roadwatch.subscription.interfaces.rest.resources;

import com.vianexo.roadwatch.subscription.domain.model.valueobjects.SubscriptionStatus;

import java.time.LocalDate;
import java.time.Instant;

public record SubscriptionResource(
        Long id,
        Long accountId,
        Long planId,
        SubscriptionStatus status,
        LocalDate startDate,
        Instant updatedAt) {
}
