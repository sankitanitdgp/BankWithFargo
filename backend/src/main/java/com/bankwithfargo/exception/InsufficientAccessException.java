package com.bankwithfargo.exception;

public class InsufficientAccessException extends RuntimeException{
    public InsufficientAccessException(String message) {
        super(message);
    }
}
