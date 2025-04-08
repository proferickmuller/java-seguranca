package com.gft.boleto.exception;

public class DeleteNotAllowedException extends RuntimeException {
    // Construtor que aceita uma mensagem
    public DeleteNotAllowedException(String message) {
        super(message);
    }

    // Construtor que aceita uma mensagem e uma causa
    public DeleteNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    // Construtor que aceita apenas uma causa
    public DeleteNotAllowedException(Throwable cause) {
        super(cause);
    }

    // Construtor padrão sem argumentos
    public DeleteNotAllowedException() {
        super();
    }
}