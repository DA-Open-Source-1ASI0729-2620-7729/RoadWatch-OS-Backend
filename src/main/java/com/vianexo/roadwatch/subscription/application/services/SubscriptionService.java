package com.vianexo.roadwatch.subscription.application.services;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Subscription;
import com.vianexo.roadwatch.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.vianexo.roadwatch.subscription.domain.repositories.PlanRepository;
import com.vianexo.roadwatch.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    public Subscription getSubscription(Long accountId) {
        return subscriptionRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found for account: %s".formatted(accountId)));
    }

    public Subscription selectPlan(Long accountId, Long planId) {
        var plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: %s".formatted(planId)));
        return subscriptionRepository.save(new Subscription(accountId, plan));
    }

    public Subscription activateSubscription(Long accountId) {
        var subscription = getSubscription(accountId);
        subscription.activate();
        return subscriptionRepository.save(subscription);
    }

    public Subscription changePlan(Long accountId, Long planId) {
        var subscription = getSubscription(accountId);
        var plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: %s".formatted(planId)));
        subscription.changePlan(plan);
        return subscriptionRepository.save(subscription);
    }

    public void cancelSubscription(Long accountId) {
        var subscription = getSubscription(accountId);
        subscription.cancel();
        subscriptionRepository.save(subscription);
    }

    public boolean validateProjectQuota(Long accountId) {
        var subscription = getSubscription(accountId);
        return subscription.getStatus() == SubscriptionStatus.ACTIVE
                && subscription.getPlan().isActive()
                && subscription.getPlan().getMaxProjects() > 0;
    }

    public boolean validateUserQuota(Long accountId) {
        var subscription = getSubscription(accountId);
        return subscription.getStatus() == SubscriptionStatus.ACTIVE
                && subscription.getPlan().isActive()
                && subscription.getPlan().getMaxUsers() > 0;
    }
}
