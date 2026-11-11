package com.vianexo.roadwatch.identityaccess.interfaces.rest.transform;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.RoleResource;

import java.util.stream.Collectors;

public final class RoleResourceFromEntityAssembler {

    private RoleResourceFromEntityAssembler() {
    }

    public static RoleResource toResourceFrom(Role role) {
        return new RoleResource(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions().stream().map(permission -> permission.getCode()).collect(Collectors.toSet()));
    }
}
