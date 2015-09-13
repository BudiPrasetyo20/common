package com.dubby.base.exception;

/**
 * Created with IntelliJ IDEA.
 * Date: 1/17/14
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseException extends Exception {
    public BaseException(String s) {
        super(s);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String s, Throwable t) {
        super(s, t);
    }
}
