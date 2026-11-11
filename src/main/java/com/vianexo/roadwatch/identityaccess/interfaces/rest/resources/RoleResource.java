package com.vianexo.roadwatch.identityaccess.interfaces.rest.resources;

import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.RoleType;

import java.util.Set;

public record RoleResource(Long id, RoleType name, String description, Set<String> permissions) {
}
