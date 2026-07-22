package com.mobi.ecommerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            WebRequest request
    ) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(
                        ex.getMessage(),
                        ex.getHttpStatus().value(),
                        request.getDescription(false)
                ));
    }

    record ErrorResponse(
            String message,
            int statusCode,
            String path
    ) {}
}
