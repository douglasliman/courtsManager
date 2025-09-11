package com.quadraOuro.domain.exception;

public class EnderecoNotFoundException extends RuntimeException {
    public EnderecoNotFoundException(String cep) {
        super("Endereço com CEP %s não encontrado".formatted(cep));
    }
}
