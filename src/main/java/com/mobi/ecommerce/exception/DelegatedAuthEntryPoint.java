package com.mobi.ecommerce.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

// Filter for first entry point in authentication security
@Component
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver handlerExceptionResolver;

    public DelegatedAuthEntryPoint(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(
            @Qualifier("handlerExceptionResolver") HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request,response,null,authException);
    }
}
