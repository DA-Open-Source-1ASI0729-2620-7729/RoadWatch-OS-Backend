package com.vianexo.roadwatch.subscription.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSubscriptionResource(
        @NotNull @Positive Long accountId,
        @NotNull @Positive Long planId) {
}
