package com.dubby.web.security.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    protected String getAllowOrigin() {
        return "";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (!getAllowOrigin().isEmpty()) {
            response.setHeader("Access-Control-Allow-Origin", getAllowOrigin());
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-auth-token, x-requested-with");
        response.setHeader("Access-Control-Max-Age", "3600");
        if (request.getMethod() != "OPTIONS") {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
