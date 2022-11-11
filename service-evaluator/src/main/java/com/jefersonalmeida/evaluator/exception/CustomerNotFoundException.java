package com.jefersonalmeida.evaluator.exception;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(final String document) {
        super("Dados do cliente não encontrado para o documento informado: %s".formatted(document));
    }
}
