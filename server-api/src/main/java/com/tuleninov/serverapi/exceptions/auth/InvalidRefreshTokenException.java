package com.tuleninov.serverapi.exceptions.auth;

public class InvalidRefreshTokenException extends Exception {

    public InvalidRefreshTokenException() {
        super();
    }

    public InvalidRefreshTokenException(Throwable cause) {
        super(cause);
    }
}
