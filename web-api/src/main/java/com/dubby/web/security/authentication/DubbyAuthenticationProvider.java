package com.dubby.web.security.authentication;

import com.dubby.security.model.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public class DubbyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    Authenticator authenticatorImpl;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authenticationToken)
            throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String id,
                                       UsernamePasswordAuthenticationToken authToken)
            throws AuthenticationException {

        String loginFrom = String.format("%1s [%2s]",
                httpServletRequest.getRemoteHost(), httpServletRequest.getRemoteAddr());

        return authenticatorImpl.login(id, authToken.getCredentials().toString(), loginFrom);

    }

}
