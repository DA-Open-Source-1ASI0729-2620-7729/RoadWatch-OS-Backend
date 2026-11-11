package com.vianexo.roadwatch.subscription.domain.model.aggregates;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.vianexo.roadwatch.subscription.domain.model.valueobjects.SubscriptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
public class Subscription extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @ManyToOne(optional = false)
    private Plan plan;

    public Subscription(Long accountId, Plan plan) {
        this.accountId = accountId;
        this.plan = plan;
        this.status = SubscriptionStatus.INACTIVE;
        this.startDate = LocalDate.now();
    }

    public void changePlan(Plan plan) {
        this.plan = plan;
    }

    public void activate() {
        status = SubscriptionStatus.ACTIVE;
    }

    public void cancel() {
        status = SubscriptionStatus.CANCELLED;
    }

}
