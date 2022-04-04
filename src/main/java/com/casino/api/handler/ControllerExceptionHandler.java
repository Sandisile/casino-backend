package com.casino.api.handler;

import com.casino.exception.InsufficientFundsException;
import com.casino.exception.ResourceNotFoundException;
import com.casino.util.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {InsufficientFundsException.class})
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public ErrorMessage insufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.I_AM_A_TEAPOT.value(),
                LocalDate.now(),
                ex.getMessage()
                );

        return message;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDate.now(),
                ex.getMessage());

        return message;
    }
}
