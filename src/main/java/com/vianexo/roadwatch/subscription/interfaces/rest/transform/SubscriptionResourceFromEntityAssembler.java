package com.vianexo.roadwatch.subscription.interfaces.rest.transform;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Subscription;
import com.vianexo.roadwatch.subscription.interfaces.rest.resources.SubscriptionResource;

public final class SubscriptionResourceFromEntityAssembler {

    private SubscriptionResourceFromEntityAssembler() {
    }

    public static SubscriptionResource toResourceFrom(Subscription subscription) {
        return new SubscriptionResource(
                subscription.getId(),
                subscription.getAccountId(),
                subscription.getPlan().getId(),
                subscription.getStatus(),
                subscription.getStartDate(),
                subscription.getUpdatedAt().toInstant());
    }
}
