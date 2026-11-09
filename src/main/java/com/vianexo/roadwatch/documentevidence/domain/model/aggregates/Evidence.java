package com.vianexo.roadwatch.documentevidence.domain.model.aggregates;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Evidence extends AuditableAbstractPersistenceEntity {

    private Long incidentId;

    private Long measurementId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String storedFileName;

    @Column(nullable = false)
    private Long uploadedByUserId;

    public Evidence(Long incidentId,
                    Long measurementId,
                    String fileName,
                    String fileType,
                    String storedFileName,
                    Long uploadedByUserId) {
        this.incidentId = incidentId;
        this.measurementId = measurementId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.storedFileName = storedFileName;
        this.uploadedByUserId = uploadedByUserId;
    }
}
