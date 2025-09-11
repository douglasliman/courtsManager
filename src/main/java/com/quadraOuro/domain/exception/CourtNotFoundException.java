package com.quadraOuro.domain.exception;

public class CourtNotFoundException extends RuntimeException {
    public CourtNotFoundException(Long id) {
        super("Court %d não encontrada".formatted(id));
    }
}
