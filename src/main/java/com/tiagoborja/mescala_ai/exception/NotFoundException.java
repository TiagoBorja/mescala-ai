package com.tiagoborja.mescala_ai.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Entity not found");
    }
}
