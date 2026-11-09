package com.vianexo.roadwatch.documentevidence.domain.model.aggregates;

import com.vianexo.roadwatch.documentevidence.domain.model.valueobjects.DocumentStatus;
import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.DocumentType;

@Getter
@Entity
@NoArgsConstructor
public class NormativeDocument extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    @Column(nullable = false)
    private Integer currentVersion;

    public NormativeDocument(Long projectId, String name) {
        this.projectId = projectId;
        this.name = name;
        this.status = DocumentStatus.REQUIRED;
        this.currentVersion = 0;
    }

    public void markAsUploaded() {
        status = DocumentStatus.UPLOADED;
        currentVersion = currentVersion + 1;
    }

    public void markAsMissing() {
        status = DocumentStatus.MISSING;
    }

}


