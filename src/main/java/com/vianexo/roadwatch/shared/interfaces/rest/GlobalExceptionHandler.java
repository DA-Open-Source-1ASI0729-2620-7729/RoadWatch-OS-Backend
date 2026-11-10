package com.vianexo.roadwatch.shared.interfaces.rest;

import com.vianexo.roadwatch.shared.application.result.ApplicationError;
import com.vianexo.roadwatch.shared.application.result.UnauthorizedException;
import com.vianexo.roadwatch.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        var details = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .reduce((first, second) -> first + "; " + second)
                .orElse("Request validation failed");
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                ApplicationError.validationError("request-body", details));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException exception) {
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                ApplicationError.authenticationError(exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException exception) {
        var message = exception.getMessage() == null ? "An unexpected error occurred" : exception.getMessage();
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                ApplicationError.unexpected("global-exception-handler", message));
    }
}
