package com.jkojote.linear.engine.graphics2d;

public class NoContextSetException extends RuntimeException {

    public NoContextSetException() { }

    public NoContextSetException(String message) {
        super(message);
    }

    public NoContextSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoContextSetException(Throwable cause) {
        super(cause);
    }

    public NoContextSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
