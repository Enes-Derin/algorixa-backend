package com.enesderin.softvra_backend.exception;

public class BaseException extends RuntimeException {
    public BaseException(ErrorMessage message) {
        super(message.prepareErrorMessage());
    }
}
