package by.eventcat.rest.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be parsed
 */

public class JwtTokenMalformedException extends AuthenticationException {


    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}

