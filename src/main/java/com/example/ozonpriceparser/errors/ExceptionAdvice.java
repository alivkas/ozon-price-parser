package com.example.ozonpriceparser.errors;

import com.example.ozonpriceparser.errors.exceptions.ElementNotFoundException;
import com.example.ozonpriceparser.errors.exceptions.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleElementNotFoundException(ElementNotFoundException ex) {
        return new ExceptionResponse(ex.CODE, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleUrlNotFoundException(UrlNotFoundException ex) {
        return new ExceptionResponse(ex.CODE, ex.getMessage());
    }
}
