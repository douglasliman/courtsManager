package com.quadraOuro.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User %d não encontrado".formatted(id));
    }
}
