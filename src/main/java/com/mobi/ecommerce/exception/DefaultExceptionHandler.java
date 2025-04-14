package com.mobi.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// it catches any exception & translate it into a payload suitable to a  client
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ApiError> handleException(
            NotFound e,
            HttpServletRequest request
    ){
           ApiError apiError= new ApiError(
                request.getServletPath(),
                e.getMessage(),
                   NOT_FOUND.value(),
                LocalDateTime.now()
        );
           return new ResponseEntity<>(apiError, NOT_FOUND);
    }
}
