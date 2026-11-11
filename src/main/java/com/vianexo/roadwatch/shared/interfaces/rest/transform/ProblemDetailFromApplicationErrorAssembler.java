package com.vianexo.roadwatch.shared.interfaces.rest.transform;

import com.vianexo.roadwatch.shared.application.result.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public final class ProblemDetailFromApplicationErrorAssembler {

    private ProblemDetailFromApplicationErrorAssembler() {
    }

    public static ResponseEntity<ProblemDetail> toResponseFromApplicationError(ApplicationError error) {
        var status = switch (error.code()) {
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case "AUTHENTICATION_ERROR" -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        var problemDetail = ProblemDetail.forStatusAndDetail(status, error.message());
        problemDetail.setType(URI.create("/api/v1/errors/" + error.code().toLowerCase().replace('_', '-')));
        problemDetail.setProperty("code", error.code());
        if (error.details() != null && !error.details().isBlank()) {
            problemDetail.setProperty("details", error.details());
        }
        return ResponseEntity.status(status).body(problemDetail);
    }
}
