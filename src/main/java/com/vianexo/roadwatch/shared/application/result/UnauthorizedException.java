package com.vianexo.roadwatch.shared.application.result;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
