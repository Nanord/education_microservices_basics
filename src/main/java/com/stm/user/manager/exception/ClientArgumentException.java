package com.stm.user.manager.exception;

public class ClientArgumentException extends IllegalArgumentException {
    public ClientArgumentException(String message) {
        super(message);
    }

    public ClientArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
