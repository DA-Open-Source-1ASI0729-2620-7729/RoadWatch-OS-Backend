package com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionPersistenceRepository extends JpaRepository<Permission, Long> {
}
