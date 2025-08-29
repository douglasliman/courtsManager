package com.quadraOuro.adapters.web.dto.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}