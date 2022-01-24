package com.stm.user.manager.exception;

public class CrudEOperationException extends Exception {

    public CrudEOperationException(String message) {
        super(message);
    }

    public CrudEOperationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
