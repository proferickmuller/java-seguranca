package br.com.fiap.boleto.exception;

public class PaymentNotAllowedException extends RuntimeException {
    // Construtor que aceita uma mensagem
    public PaymentNotAllowedException(String message) {
        super(message);
    }

    // Construtor que aceita uma mensagem e uma causa
    public PaymentNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    // Construtor que aceita apenas uma causa
    public PaymentNotAllowedException(Throwable cause) {
        super(cause);
    }

    // Construtor padrão sem argumentos
    public PaymentNotAllowedException() {
        super();
    }
}