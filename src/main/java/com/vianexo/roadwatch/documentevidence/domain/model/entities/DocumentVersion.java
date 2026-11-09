package com.vianexo.roadwatch.documentevidence.domain.model.entities;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DocumentVersion extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long documentId;

    @Column(nullable = false)
    private Integer versionNumber;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String storedFileName;

    @Column(nullable = false)
    private Long uploadedByUserId;

    public DocumentVersion(Long documentId,
                           Integer versionNumber,
                           String filetName,
                           String storedFileName,
                           Long uploadedByUserId) {
        this.documentId = documentId;
        this.versionNumber = versionNumber;
        this.fileName = filetName;
        this.storedFileName = storedFileName;
        this.uploadedByUserId = uploadedByUserId;
    }
}
