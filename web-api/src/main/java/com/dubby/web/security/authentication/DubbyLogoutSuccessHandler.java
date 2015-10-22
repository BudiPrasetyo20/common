package com.dubby.web.security.authentication;

import com.dubby.security.model.Authenticator;
import com.dubby.security.model.SecurityLogger;
import com.dubby.security.model.entity.DubbyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DubbyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    Authenticator authenticatorImpl;

    @Autowired
    SecurityLogger securityLogger;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        if (authentication != null && authentication.getDetails() != null) {
            try {

                DubbyUser user = (DubbyUser) authentication.getPrincipal();

                httpServletRequest.getSession().invalidate();
                authenticatorImpl.logout(user.getUsername());
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {

                securityLogger.logError(e, "security.error.logout");
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

    }
}
