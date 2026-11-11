package com.vianexo.roadwatch.subscription.interfaces.rest.controllers;

import com.vianexo.roadwatch.identityaccess.application.outboundservices.tokens.TokenProvider;
import com.vianexo.roadwatch.identityaccess.application.services.UserService;
import com.vianexo.roadwatch.subscription.application.services.SubscriptionService;
import com.vianexo.roadwatch.subscription.interfaces.rest.resources.CreateSubscriptionResource;
import com.vianexo.roadwatch.subscription.interfaces.rest.resources.SubscriptionResource;
import com.vianexo.roadwatch.subscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public SubscriptionController(
            SubscriptionService subscriptionService,
            UserService userService,
            TokenProvider tokenProvider) {
        this.subscriptionService = subscriptionService;

        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Operation(summary = "Get current subscription")
    @ApiResponse(responseCode = "200", description = "Current subscription returned")
    @ApiResponse(responseCode = "400", description = "Invalid authentication token or subscription account")
    @GetMapping("/current")

    public ResponseEntity<SubscriptionResource> getCurrentSubscription(

            @RequestHeader("Authorization") String authorization) {
        var subscription = subscriptionService.getSubscription(currentAccountId(authorization));
        return ResponseEntity.ok(SubscriptionResourceFromEntityAssembler.toResourceFrom(subscription));
    }

    @Operation(summary = "Change current subscription plan")
    @ApiResponse(responseCode = "200", description = "Subscription plan changed")
    @PatchMapping("/current/plan/{planId}")
    public ResponseEntity<SubscriptionResource> changeCurrentSubscriptionPlan(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long planId) {
        var subscription = subscriptionService.changePlan(currentAccountId(authorization), planId);
        return ResponseEntity.ok(SubscriptionResourceFromEntityAssembler.toResourceFrom(subscription));
    }

    @Operation(summary = "Create subscription")
    @ApiResponse(responseCode = "201", description = "Subscription created")
    @PostMapping
    public ResponseEntity<SubscriptionResource> createSubscription(
            @Valid @RequestBody CreateSubscriptionResource resource) {
        var subscription = subscriptionService.selectPlan(resource.accountId(), resource.planId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SubscriptionResourceFromEntityAssembler.toResourceFrom(subscription));
    }

    @Operation(summary = "Activate subscription")
    @ApiResponse(responseCode = "200", description = "Subscription activated")
    @PatchMapping("/{accountId}/activation")
    public ResponseEntity<SubscriptionResource> activateSubscription(@PathVariable Long accountId) {
        var subscription = subscriptionService.activateSubscription(accountId);
        return ResponseEntity.ok(SubscriptionResourceFromEntityAssembler.toResourceFrom(subscription));
    }

    private String extractToken(String authorization) {
        
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authentication token");
        }

        var token = authorization.substring(7);
        if (!tokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid authentication token");
        }


        return token;
    }

    private Long currentAccountId(String authorization) {
        var userId = tokenProvider.getUserId(extractToken(authorization));
        var user = userService.findById(userId);

        if (user.getSubscriptionAccountId() == null) {
            throw new IllegalArgumentException("Subscription account is not assigned to the current user");
        }

        return user.getSubscriptionAccountId();
    }
}
