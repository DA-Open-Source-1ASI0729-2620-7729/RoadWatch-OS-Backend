package com.vianexo.roadwatch.identityaccess.application.services;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Permission;
import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;
import com.vianexo.roadwatch.identityaccess.domain.repositories.PermissionRepository;
import com.vianexo.roadwatch.identityaccess.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void assignPermission(Long roleId, Long permissionId) {
        var role = findRole(roleId);
        role.addPermission(findPermission(permissionId));
        roleRepository.save(role);
    }

    public void removePermission(Long roleId, Long permissionId) {
        var role = findRole(roleId);
        role.removePermission(findPermission(permissionId));
        roleRepository.save(role);
    }

    private Role findRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: %s".formatted(id)));
    }

    private Permission findPermission(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: %s".formatted(id)));
    }
}
