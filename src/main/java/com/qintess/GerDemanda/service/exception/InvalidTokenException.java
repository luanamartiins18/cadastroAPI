package com.qintess.GerDemanda.service.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token inválido.");
    }
}
