package com.vianexo.roadwatch.documentevidence.domain.model.aggregates;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class RequiredDocument extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private long projectId;

    @Column(nullable = false)
    private String milestoneName;

    @Column(nullable = false)
    private String documentName;

    @Column(nullable = false)
    private boolean mandatory;

    private LocalDate dueDate;

    public RequiredDocument(long projectId,
                            String milestoneName,
                            String documentName,
                            boolean mandatory,
                            LocalDate dueDate) {
        this.projectId = projectId;
        this.milestoneName = milestoneName;
        this.documentName = documentName;
        this.mandatory = mandatory;
        this.dueDate = dueDate;
    }
}
