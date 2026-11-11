package com.vianexo.roadwatch.identityaccess.interfaces.rest.controllers;

import com.vianexo.roadwatch.identityaccess.application.services.UserService;
import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.RegisterUserResource;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.UserResource;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register user")
    @PostMapping
    public ResponseEntity<UserResource> registerUser(@Valid @RequestBody RegisterUserResource resource) {
        var user = new User(resource.firstName(), resource.lastName(), resource.email(), resource.password());
        var registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResourceFromEntityAssembler.toResourceFrom(registeredUser));
    }

    @Operation(summary = "Get user by identifier")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFrom(userService.findById(userId)));
    }

    @Operation(summary = "Get user by email")
    @GetMapping(params = "email")
    public ResponseEntity<UserResource> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFrom(userService.findByEmail(email)));
    }

    @Operation(summary = "Deactivate user")
    @PatchMapping("/{userId}/deactivation")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign role to user")
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<Void> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.assignRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }

}
