package com.vianexo.roadwatch.identityaccess.interfaces.rest.controllers;

import com.vianexo.roadwatch.identityaccess.application.services.AuthenticationService;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.AuthenticationRequestResource;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.AuthenticationResource;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.transform.AuthenticationResourceFromAuthTokenAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Authenticate user")
    @ApiResponse(responseCode = "200", description = "Authentication token issued")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResource> login(
            @Valid @RequestBody AuthenticationRequestResource resource) {
        var authToken = authenticationService.authenticate(resource.email(), resource.password());
        return ResponseEntity.ok(AuthenticationResourceFromAuthTokenAssembler.toResourceFrom(authToken));
    }
}
