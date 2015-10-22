package com.dubby.security.model.entity;


import com.dubby.security.exception.DubbyAuthenticationException;

public class SkyAuthenticationResult {

    DubbyAuthenticationException dubbyAuthenticationException;
    User user;

    public SkyAuthenticationResult(DubbyAuthenticationException dubbyAuthenticationException, User user) {
        this.dubbyAuthenticationException = dubbyAuthenticationException;
        this.user = user;
    }

    public DubbyAuthenticationException getDubbyAuthenticationException() {
        return dubbyAuthenticationException;
    }

    public User getUser() {
        return user;
    }
}
