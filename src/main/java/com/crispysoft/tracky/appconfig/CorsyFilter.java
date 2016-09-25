package com.crispysoft.tracky.appconfig;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Default @CrossOrigin annotation is not providing the PUT method in "Access-Control-Allow-Methods"
 * Maybe it's a bug, but anyway - it's better to control the headers manually
 *
 * User: david
 * Date: 24.09.2016
 * Time: 14:06
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class CorsyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS"); // will need to enable other methods when/as implemented
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                ((HttpServletRequest) servletRequest).getHeader("Access-Control-Request-Headers"));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
