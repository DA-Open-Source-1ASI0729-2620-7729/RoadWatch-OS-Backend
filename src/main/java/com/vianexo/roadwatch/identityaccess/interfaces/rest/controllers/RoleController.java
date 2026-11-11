package com.vianexo.roadwatch.identityaccess.interfaces.rest.controllers;

import com.vianexo.roadwatch.identityaccess.application.services.RoleService;
import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.CreateRoleResource;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.RoleResource;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.transform.RoleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Create role")
    @PostMapping
    public ResponseEntity<RoleResource> createRole(@Valid @RequestBody CreateRoleResource resource) {
        var role = roleService.createRole(new Role(resource.name(), resource.description()));
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleResourceFromEntityAssembler.toResourceFrom(role));
    }

    @Operation(summary = "Assign permission to role")
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> assignPermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        roleService.assignPermission(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove permission from role")
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> removePermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        roleService.removePermission(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }
}
