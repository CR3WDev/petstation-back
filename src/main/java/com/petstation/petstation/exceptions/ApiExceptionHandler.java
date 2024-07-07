package com.petstation.petstation.exceptions;

import com.petstation.petstation.dtos.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j(topic = "GLOBAL_EXCEPTION_ERROR")
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handlerNotFound(NotFoundException ex) {
        return new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getCause().getMessage()
        );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllUncaughtException(Exception ex) {
        return new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unknown error occurred",
                ex.getCause().getMessage()
        );
    }
}