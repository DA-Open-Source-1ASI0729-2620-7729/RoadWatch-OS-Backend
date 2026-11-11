package com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;
import com.vianexo.roadwatch.identityaccess.domain.repositories.RoleRepository;
import com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.repositories.RolePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RolePersistenceRepository rolePersistenceRepository;

    public RoleRepositoryImpl(RolePersistenceRepository rolePersistenceRepository) {
        this.rolePersistenceRepository = rolePersistenceRepository;
    }

    @Override
    public Role save(Role role) {
        return rolePersistenceRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return rolePersistenceRepository.findById(id);
    }
}
