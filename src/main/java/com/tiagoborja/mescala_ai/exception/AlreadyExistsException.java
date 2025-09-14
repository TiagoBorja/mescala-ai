package com.tiagoborja.mescala_ai.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException() {
        super("The resource you are trying to create already exists");
    }
    public AlreadyExistsException(String message) {
        super(message);
    }
}
