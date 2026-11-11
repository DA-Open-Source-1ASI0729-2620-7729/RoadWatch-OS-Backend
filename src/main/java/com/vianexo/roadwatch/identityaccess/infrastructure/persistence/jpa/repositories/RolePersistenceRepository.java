package com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePersistenceRepository extends JpaRepository<Role, Long> {
}
