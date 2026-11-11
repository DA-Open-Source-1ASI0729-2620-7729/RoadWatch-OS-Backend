package com.vianexo.roadwatch.subscription.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Subscription;
import com.vianexo.roadwatch.subscription.domain.repositories.SubscriptionRepository;
import com.vianexo.roadwatch.subscription.infrastructure.persistence.jpa.repositories.SubscriptionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionPersistenceRepository subscriptionPersistenceRepository;

    public SubscriptionRepositoryImpl(SubscriptionPersistenceRepository subscriptionPersistenceRepository) {
        this.subscriptionPersistenceRepository = subscriptionPersistenceRepository;
    }

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionPersistenceRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> findByAccountId(Long accountId) {
        return subscriptionPersistenceRepository.findByAccountId(accountId);
    }
}
