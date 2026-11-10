package com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Permission;
import com.vianexo.roadwatch.identityaccess.domain.repositories.PermissionRepository;
import com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.repositories.PermissionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionPersistenceRepository permissionPersistenceRepository;

    public PermissionRepositoryImpl(PermissionPersistenceRepository permissionPersistenceRepository) {
        this.permissionPersistenceRepository = permissionPersistenceRepository;
    }

    @Override
    public Permission save(Permission permission) {
        return permissionPersistenceRepository.save(permission);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionPersistenceRepository.findById(id);
    }
}
