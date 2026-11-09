package com.vianexo.roadwatch.projectmanagement.interfaces.rest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.vianexo.roadwatch.projectmanagement.interfaces.rest")
public class ProjectManagementExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> handleInvalidInput(RuntimeException exception) {
        return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(NoSuchElementException exception) {
        return error(HttpStatus.NOT_FOUND, "NOT_FOUND", exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleConflict(IllegalStateException exception) {
        return error(HttpStatus.CONFLICT, "INVALID_STATE", exception.getMessage());
    }

    private static ResponseEntity<ProblemDetail> error(HttpStatus status, String code, String message) {
        var problem = ProblemDetail.forStatusAndDetail(status, message);
        problem.setType(URI.create("/api/v1/errors/" + code.toLowerCase().replace('_', '-')));
        problem.setProperty("code", code);
        return ResponseEntity.status(status).body(problem);
    }
}
