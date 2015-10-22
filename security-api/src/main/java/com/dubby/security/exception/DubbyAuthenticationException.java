package com.dubby.security.exception;

import com.dubby.base.exception.BaseException;

public class DubbyAuthenticationException extends BaseException {

    public DubbyAuthenticationException(String s) {
        super(s);
    }

    public DubbyAuthenticationException(Throwable cause) {
        super(cause);
    }

    public DubbyAuthenticationException(String s, Throwable t) {
        super(s, t);
    }
}
