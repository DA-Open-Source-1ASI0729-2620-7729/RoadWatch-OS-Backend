package com.vianexo.roadwatch.subscription.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.subscription.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPersistenceRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByAccountId(Long accountId);
}
