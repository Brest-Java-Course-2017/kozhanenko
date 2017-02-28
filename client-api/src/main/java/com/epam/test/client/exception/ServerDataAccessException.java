package com.epam.test.client.exception;

/**
 * Created by andrei on 28.2.17.
 */
public class ServerDataAccessException extends RuntimeException {

    public ServerDataAccessException(String message) {
        super(message);
    }

    public ServerDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}