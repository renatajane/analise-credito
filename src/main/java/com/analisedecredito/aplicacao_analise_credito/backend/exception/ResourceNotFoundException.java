package com.analisedecredito.aplicacao_analise_credito.backend.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
