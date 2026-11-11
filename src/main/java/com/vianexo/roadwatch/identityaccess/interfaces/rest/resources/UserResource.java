package com.vianexo.roadwatch.identityaccess.interfaces.rest.resources;

import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.RoleType;
import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.UserStatus;

import java.time.Instant;
import java.util.Set;

public record UserResource(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserStatus status,
        Set<RoleType> roles,
        Instant createdAt,
        Instant updatedAt) {
}
