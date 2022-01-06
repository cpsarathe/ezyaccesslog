package com.ezy.access.exception;

public class AccessLogException extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    public AccessLogException(String message) {
        super(message);
    }
    public AccessLogException(String message, Throwable cause) {
        super(message, cause);
    }
}
