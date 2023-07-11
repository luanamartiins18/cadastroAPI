package com.qintess.GerDemanda.service.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException() {
        super("Usuário não encontrado.");
    }
}
