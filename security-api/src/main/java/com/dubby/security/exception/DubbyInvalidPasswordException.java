package com.dubby.security.exception;

public class DubbyInvalidPasswordException extends DubbyAuthenticationException {

    public DubbyInvalidPasswordException(String s) {
        super(s);
    }

    public DubbyInvalidPasswordException(Throwable cause) {
        super(cause);
    }

    public DubbyInvalidPasswordException(String s, Throwable t) {
        super(s, t);
    }
}
