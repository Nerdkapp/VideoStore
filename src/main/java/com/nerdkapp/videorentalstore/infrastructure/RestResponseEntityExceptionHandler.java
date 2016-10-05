package com.nerdkapp.videorentalstore.infrastructure;

import com.nerdkapp.videorentalstore.domain.rental.RentalNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(value = {RentalNotFoundException.class, MovieNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null,
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}