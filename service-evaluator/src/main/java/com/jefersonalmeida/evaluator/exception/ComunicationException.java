package com.jefersonalmeida.evaluator.exception;

public class ComunicationException extends Exception {

    private final Integer status;

    public ComunicationException(final String message, final Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
