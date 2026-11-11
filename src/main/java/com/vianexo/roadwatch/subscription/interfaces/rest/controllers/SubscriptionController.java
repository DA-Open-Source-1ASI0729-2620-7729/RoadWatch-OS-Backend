package com.vianexo.roadwatch.subscription.interfaces.rest.controllers;

import com.vianexo.roadwatch.identityaccess.application.outboundservices.tokens.TokenProvider;
import com.vianexo.roadwatch.identityaccess.application.services.UserService;
import com.vianexo.roadwatch.subscription.application.services.SubscriptionService;
import com.vianexo.roadwatch.subscription.interfaces.rest.resources.SubscriptionResource;
import com.vianexo.roadwatch.subscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        var token = extractToken(authorization);
        var userId = tokenProvider.getUserId(token);
        var user = userService.findById(userId);

        if (user.getSubscriptionAccountId() == null) {
            throw new IllegalArgumentException("Subscription account is not assigned to the current user");
        }

        var subscription = subscriptionService.getSubscription(user.getSubscriptionAccountId());
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
}
