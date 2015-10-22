package com.dubby.security.exception;

import org.springframework.security.core.AuthenticationException;

public class MultipleLoginException extends AuthenticationException {

    public MultipleLoginException(String msg) {
        super(msg);
    }

    public MultipleLoginException(String msg, Throwable t) {
        super(msg, t);
    }
}
