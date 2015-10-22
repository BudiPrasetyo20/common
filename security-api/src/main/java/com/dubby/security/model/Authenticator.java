package com.dubby.security.model;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface Authenticator {
    public UserDetails login(String userName, String password, String loginFrom)
            throws AuthenticationException;

    public void logout(String userName) throws AuthenticationException;
}
