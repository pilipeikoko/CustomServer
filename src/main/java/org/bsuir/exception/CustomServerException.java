package org.bsuir.exception;

public class CustomServerException extends Exception {
    public CustomServerException() {
    }

    public CustomServerException(String message) {
        super(message);
    }

    public CustomServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomServerException(Throwable cause) {
        super(cause);
    }
}
