package com.vianexo.roadwatch.subscription.domain.repositories;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Subscription;

import java.util.Optional;

public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    Optional<Subscription> findByAccountId(Long accountId);
}
