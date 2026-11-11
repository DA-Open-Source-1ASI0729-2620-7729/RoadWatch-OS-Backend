package com.vianexo.roadwatch.shared.application.result;

public record ApplicationError(String code, String message, String details) {

    public static ApplicationError validationError(String fieldOrConcept, String reason) {
        return new ApplicationError("VALIDATION_ERROR", "Validation failed: %s".formatted(fieldOrConcept), reason);
    }

    public static ApplicationError unexpected(String context, String reason) {
        return new ApplicationError("UNEXPECTED_ERROR", "Unexpected error in %s".formatted(context), reason);
    }

    public static ApplicationError authenticationError(String reason) {
        return new ApplicationError("AUTHENTICATION_ERROR", "Authentication failed", reason);
    }
}
