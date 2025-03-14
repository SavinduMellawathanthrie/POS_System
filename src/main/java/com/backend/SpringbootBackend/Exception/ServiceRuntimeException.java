package com.backend.SpringbootBackend.Exception;

public class ServiceRuntimeException extends RuntimeException {
    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRuntimeException(String message) {
        super(message);
    }
}
