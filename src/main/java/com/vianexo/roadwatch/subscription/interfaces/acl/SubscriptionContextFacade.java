package com.vianexo.roadwatch.subscription.interfaces.acl;

import com.vianexo.roadwatch.subscription.application.services.SubscriptionService;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionContextFacade {

    private final SubscriptionService subscriptionService;

    public SubscriptionContextFacade(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public boolean validateProjectQuota(Long accountId) {
        return subscriptionService.validateProjectQuota(accountId);
    }

    public boolean validateUserQuota(Long accountId) {
        return subscriptionService.validateUserQuota(accountId);
    }
}
