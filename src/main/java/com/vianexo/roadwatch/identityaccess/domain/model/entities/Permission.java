package com.vianexo.roadwatch.identityaccess.domain.model.entities;

import com.vianexo.roadwatch.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "permissions")
@NoArgsConstructor
public class Permission extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    public Permission(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
