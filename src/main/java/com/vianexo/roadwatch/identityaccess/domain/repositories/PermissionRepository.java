package com.vianexo.roadwatch.identityaccess.domain.repositories;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Permission;

import java.util.Optional;

public interface PermissionRepository {

    Permission save(Permission permission);

    Optional<Permission> findById(Long id);
}
